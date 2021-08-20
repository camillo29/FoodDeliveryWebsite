import React, { useState, useEffect } from 'react';
import { useCookies } from 'react-cookie';
import { verifyRole, getDishes, addNewDish, removeDish} from '../Fetch';
import { useHistory } from 'react-router-dom';
import Dish from '../Objects/Dish';

import '../../App.css';
/**
 * Component which renders Manage dishes page, allowing admin to add new or remove existing ones
 */
const ManageDishes = () => {
    const [cookie, setCookie, removeCookie] = useCookies(['userToken']);
    const [authorized, setAuthorized] = useState(false);
    const [dishes, setDishes] = useState([]);

    const [name, setName] = useState('Dish name');
    const [description, setDescription] = useState('Dish description');
    const [price, setPrice] = useState(0.0);

    const [error, setError] = useState('');
    const [response, setResponse] = useState('');

    const [refresh, setRefresh] = useState(false);
    let history = useHistory();

    /**
     *  function which handles submitting new dish, after checking if all requirements have been fulfilled 
     */
    const handleSubmit = () => {
        if ((name && name !== '' && name !== 'Dish name') && 
            (description && description !== '' && description !== 'Dish description') && 
            (price && price !== 0.0 && price > 0.0)) {
            addNewDish(cookie.userToken.token, name, description, price, setResponse, setRefresh, refresh);
            setName('Dish name'); setDescription('Dish description'); setPrice(0.0);
            setError(''); 
        } else setError('All fields must be filled!');
    }
    /**
     * function which handles deleting dishes
     * @param {any} id ID of dish to be deleted
     */
    const handleDelete = (id) => {
        removeDish(cookie.userToken.token, id, setRefresh, refresh);
    }
    /**
     * UseEffect that gets all dishes if admin was verified and removes userToken if it expired every refresh and userToken change
     */
    useEffect(() => {
        getDishes(setDishes);
        if (cookie.userToken) {
            verifyRole(cookie.userToken.token, "ADMIN", setAuthorized);
        }
        else {
            removeCookie('userToken');
            history.push('/');
            }
    }, [refresh, cookie.userToken])
    
    if (!authorized) return <h1> NOT AUTHORIZED </h1>

    else return (
        <div>
            <h1> MANAGE DISHES </h1>
            <div>
                <div style={{ float: 'left', margin: '0 auto', width:'50%'}}>
                    {dishes.map((dish)=>{
                        return (
                            <div className = 'Dish' key={dish.id}>
                                <Dish dish={dish}/>
                                <button type='button' className = 'navButton' style = {{margin:'5px'}} onClick={() => handleDelete(dish.id)}>X</button>
                            </div>
                            );
                    })}
                </div>
            </div>
            <div style = {{textAlign: 'left'}}>
                <form>
                    <ul className='NoBulletList'>
                        <li><input type = 'text' style = {{marginTop: '5px', width: '30%'}} value = {name} onChange = {(e)=>setName(e.target.value)} /></li>
                        <li><textarea type='text' style={{ marginTop: '5px' }} rows="4" cols="50" value = {description} onChange = {(e) => setDescription(e.target.value)}/></li>
                        <li>Price:<input type='number' style={{ marginTop: '5px' }} min = '0' step = "0.01"  value={price} onChange={(e) => setPrice(e.target.value)} /></li>
                    </ul>
                    <button type = 'button' style = {{margin: 0}} className = 'navButton' onClick = {()=>handleSubmit()}> Submit new dish </button>
                    <div className = 'Error'>{error}</div>
                    <p> {response} </p>
                </form>
            </div>
        </div>
        );
}

export default ManageDishes;