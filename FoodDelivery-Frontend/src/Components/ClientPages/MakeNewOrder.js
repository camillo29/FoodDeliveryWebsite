import { useState, useEffect } from 'react';
import { getDishes, fetchOffices, addOrder } from '../Fetch';
import { useCookies } from 'react-cookie';
import { useHistory } from 'react-router-dom';

import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';

import Dish from '../Objects/Dish';

import '../../App.css'

/**
 * Component that renders Make new order page, allows user to create new order
 */
const MakeNewOrder = () => {
    const [cookie, setCookie, removeCookie] = useCookies(['userToken']);
    const [dishes, setDishes] = useState([]);
    const [offices, setOffices] = useState([]);

    const [selectedDishes, setSelectedDishes] = useState([]);
    const [selectedOffice, setSelectedOffice] = useState();

    const [totalPrice, setTotalPrice] = useState(.0);
    const [address, setAddress] = useState('');

    const [error, setError] = useState('');
    const [response, setResponse] = useState('');
    const [refresh, setRefresh] = useState(false);
    let history = useHistory();

    /**
     * function which calculates total price of choosed dishes
     */
    const calculatePrice = () => {
        var price = 0.0;
        selectedDishes.map((dish)=>{
            price += dish.price * dish.amount;
        })
        price = Math.floor(price * 100) / 100;
        setTotalPrice(price);
        return;
    }
    /**
     * function which handles adding dishes to order
     * @param {any} dish dish to be added to order
     */
    const handleDishAdd = (dish) => {
        var newDishes = selectedDishes;
        newDishes.push(dish);
        setSelectedDishes(newDishes);
        setDishes(dishes.filter(item => item !== dish));
        setRefresh(!refresh)
    }
    /**
     * function which handles removing dishes from order
     * @param {any} dish dish to be removed from order
     */
    const handleDishRemove = (dish) => {
        setSelectedDishes(selectedDishes.filter(item => item !== dish));
        dishes.push(dish);
        setRefresh(!refresh);
    }
    /**
     * function which handles changing amount of one dish
     * @param {any} dish dish which amount will be changed
     * @param {any} value value to add of substract from dish amount (1 or -1)
     */
    const changeAmount = (dish, value) => {
        if(dish.amount + value > 0){
            dish.amount = dish.amount + value;
            setRefresh(!refresh);
        }
    }
    /**
     * function which handles submitting order and checks if all requirements are fulfilled
     */
    const handleSubmit = () => {
        if(selectedDishes.length>0 && address !== '' && address && selectedOffice){
            console.log(selectedOffice);
            addOrder(cookie.userToken.token, selectedDishes, selectedOffice, address, setResponse);
            setSelectedDishes([]); setAddress(''); setTotalPrice(0.0);
        } else {
            setResponse('');
            if (selectedDishes.length == 0) setError('You must select at least one dish.');
            else if (address === '' || !address) setError("You must fill in address.");
            else if (!selectedOffice) setError("You must choose office.");
        }
    }

    /**
     * UseEffect which fetches dishes, calls calculatePrice and removes userToken every refresh and userToken changes
     */
    useEffect(()=>{
        if(cookie.userToken){
            if(selectedDishes.length===0){
                getDishes(setDishes);
            }
            fetchOffices(setOffices);
            calculatePrice();
        } else {
            removeCookie('userToken');
            history.push('/');
        }
    }, [refresh, cookie.userToken])

    return (
        <div>
            <h1> MAKE NEW ORDER </h1>
            <div id = 'Order' className = 'LeftPanel'>
                <h2> ORDER </h2>
                Address: <input type='text' value={address} style = {{width: "80%", marginBottom: '10px'}} onChange={(e) => setAddress(e.target.value)} />
                <div>
                    <Autocomplete
                        options={offices}
                        getOptionLabel={(option) => option.city + ', ' + option.street +' '+ option.postCode}
                        style = {{width: '50%', margin: '0 auto'}}
                        onChange={(event, value) => setSelectedOffice(value)}
                        renderInput={(params) =>
                            <TextField {...params} label="Office" variant="outlined" />}
                    />
                </div>
                {selectedDishes.map((dish)=>{
                    return (
                        <div key = {dish.id} className = 'Dish'>   
                            <Dish dish = {dish} />
                            <button type='button' className='navButton' style={{ margin: 0, marginRight: '5px'}} onClick = {()=>changeAmount(dish, -1)}> - </button>
                            x {dish.amount}
                            <button type='button' className='navButton' style={{ margin: 0, marginRight: '5px' }} onClick={() => changeAmount(dish, 1)}> + </button>
                            <p><button type = 'button' className = 'navButton' style = {{margin:0}} onClick = {()=>handleDishRemove(dish)}> Remove from order </button></p>
                        </div>
                    );
                })}
                <p> ____________________________________</p>
                <h2> Total price: {totalPrice} PLN </h2>
                <button type = 'button' className = 'navButton' style = {{margin:0}} onClick = {()=>handleSubmit()}> Submit order </button>
                <h2 className = 'Error'> {error} </h2> 
                <h2> {response} </h2>
            </div>
            <div id='Dishes' style={{ float: 'right', margin: '0 auto', width: '50%' }}>
                <h2> DISHES </h2>
                {dishes.map((dish)=>{
                    return(
                        <div key={dish.id} className='Dish'>
                        <Dish dish = {dish}/>
                            <button type='button' className='navButton' style={{ margin: 0 }} onClick = {()=>handleDishAdd(dish)}>Add to order</button>
                    </div>
                     );
                })}
            </div>
        </div>
        );
}

export default MakeNewOrder;