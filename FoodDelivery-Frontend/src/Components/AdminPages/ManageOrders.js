import React, { useState, useEffect } from 'react';
import { useCookies } from 'react-cookie';
import { useHistory } from 'react-router-dom';
import { verifyRole, getAllOrders, removeOrder } from '../Fetch';

import Order from '../Objects/Order';

import '../../App.css'
/**
 * Component which renders Manage orders page allowing admin to removing existing orders
 */
const ManageOrders = () => {
    const [cookie, setCookie, removeCookie] = useCookies(['userToken']);
    const [authorized, setAuthorized] = useState(false);
    const [orders, setOrders] = useState([]);

    const [refresh, setRefresh] = useState(false);
    let history = useHistory();
    /**
     * function which handles removing orders
     * @param {any} id ID of order to be deleted
     */
    const handleRemove = (id) => {
        removeOrder(cookie.userToken.token, id, setRefresh, refresh);
    }

    /**
     * UseEffect that gets all orders if admin was verified and removes userToken if it expired every refresh and userToken change
     */ 
    useEffect(() => {
        if (cookie.userToken) {
            verifyRole(cookie.userToken.token, "ADMIN", setAuthorized);
            getAllOrders(cookie.userToken.token, setOrders);
        } else {
            removeCookie('userToken');
            history.push('/');
            }
    }, [refresh, cookie.userToken])

    if (!authorized) return <h1> NOT AUTHORIZED </h1>

    else return (
        <div>
            <h1> MANAGE ORDERS </h1>
            {orders.map((order) => {
                return (
                    <div key = {order.id} className = 'Order'>
                        <Order order={order} />
                        <button type = 'button' className = 'navButton' style = {{margin: 0}} onClick = {() => handleRemove(order.id)}> X </button>
                    </div>
                    );
            })}
        </div>
    );
}

export default ManageOrders;