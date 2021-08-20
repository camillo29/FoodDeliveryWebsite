import React, { useState, useEffect } from 'react';
import { useCookies } from 'react-cookie';
import { useHistory } from 'react-router-dom';
import { verifyRole, fetchOffices, addOffice, removeOffice} from '../Fetch';
import Office from '../Objects/Office';
import '../../App.css'
/**
 * Component which renders Manage offices page, allowing admin to add and remove offices
 */
const ManageOffices = () => {
    const [cookie, setCookie, removeCookie] = useCookies(['userToken']);
    const [authorized, setAuthorized] = useState(false);
    const [offices, setOffices] = useState([]);

    const [city, setCity] = useState('City');
    const [street, setStreet] = useState('Street');
    const [postCode, setPostCode] = useState('Post code');
    const [phoneNumber, setPhoneNumber] = useState(0);

    const [refresh, setRefresh] = useState(false);
    const [response, setResponse] = useState('');
    const [error, setError] = useState('');
    let history = useHistory();

    /**
     * function which handles submitting new office after checking if all requirements are fulfilled
     */
    const handleSubmit = () => {
        if((street && street !== '' && street!=='Street') &&
            (city && city !== '' && city !=="City") && 
            (postCode && postCode !== '' && postCode !== 'Post code') &&
            (phoneNumber && phoneNumber !== '' && phoneNumber !== 0)){

            addOffice(cookie.userToken.token, city, street, postCode, phoneNumber, setRefresh, refresh, setResponse);
            setCity('City'); setStreet('Street'); setPostCode('Post code'); setPhoneNumber(0);
            setError('');
        } 
        else {
            setError('All fields must be filled!');
            setResponse('');
        }
    }
    /**
     * function which handles office removal
     * @param {any} id ID of office to be removed
     */
    const handleRemove = (id) => {
        removeOffice(cookie.userToken.token, id, setRefresh, refresh);
    }
    /**
     * UseEffect that gets all offices if admin was verified and removes userToken if it expired every refresh and userToken change
     */
    useEffect(() => {
        fetchOffices(setOffices);
        if (cookie.userToken)
            verifyRole(cookie.userToken.token, "ADMIN", setAuthorized);
        else {
            removeCookie('userToken');
            history.push('/');
        }
    }, [refresh, cookie.userToken])


    if (!authorized) return <h1> NOT AUTHORIZED </h1>

    else return (
        <div>
        <h1> MANAGE OFFICES </h1>
            <div className='Offices' style = {{float: 'left', margin: '0 auto', width:'50%'}}>
            {offices.map((office) => {
                return (
                    <div key = {office.id} className = 'Office'>
                        <Office office={office} />
                        <button type='button' className='navButton' style={{ margin: 0}} onClick = {()=>handleRemove(office.id)}> X </button>
                    </div>
                    );
            })}
            </div>
            <div style = {{textAlign: 'left'}}>
                <form>
                    <label className="Label" style={{ float: 'none'}}>City:</label><input type='text' className="Input" style = {{float:'none', width: '20%'}} value={city} onChange={(e) => setCity(e.target.value)} />
                    <label className="Label" style={{ float: 'none' }}>Street:</label><input type='text' className="Input" style={{ float: 'none', width: '20%' }} value={street} onChange={(e) => setStreet(e.target.value)} />
                    <label className="Label" style={{ float: 'none' }}>Post code:</label><input type='text' className="Input" style={{ float: 'none', width: '20%' }} value={postCode} onChange={(e) => setPostCode(e.target.value)} />
                    <label className="Label" style={{ float: 'none' }}>Phone number:</label><input type='number' className="Input" style={{ float: 'none', width: '20%' }} value={phoneNumber} onChange={(e) => setPhoneNumber(e.target.value)} />
                    <button type='button' className='navButton' style={{ margin: 0, }} onClick = {()=>handleSubmit()}> Submit new office </button>
                    <h2 style = {{textAlign: 'center'}} className = 'Error'> {error} </h2>
                    <h2 style = {{textAlign: 'center'}}> {response} </h2>
                </form>
            </div>
        </div>

    );
}

export default ManageOffices;