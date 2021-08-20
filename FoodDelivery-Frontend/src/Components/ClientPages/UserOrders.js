import { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { getUserOrders, removeOrder, deliverOrder } from '../Fetch';
import { useCookies } from 'react-cookie';
import { useHistory } from 'react-router-dom';
import Order from '../Objects/Order';

import '../../App.css';
/**
 * Component that renders user orders information
 * @param {any} props
 *              {location.state?.role} - user role
 *              {location.state?.criteria} - active or delivered order flag
 */
const UserOrders = (props) => {
    const location = useLocation();
    const role = location.state?.role;
    const criteria = location.state?.criteria;
    const [cookie, setCookie, removeCookie] = useCookies(['userToken']);
    const [orders, setOrders] = useState([]);
    let history = useHistory();
    
    const [showDeliverButton, setShowDeliverButton] = useState(false);

    const [refresh, setRefresh] = useState(false);

    /**
     * Function that handles employee deliver button, calls deliverOrder function from Fetch
     * @param {any} id ID of order to be marked as delivered
     */
    const handleDeliver = (id) => {
        deliverOrder(cookie.userToken.token, id);
        setRefresh(!refresh)
    }
    /**
     * UseEffect that fetches user orders, decides wheter employee deliver button should be displayed
     * and removes userToken if it expires every refresh, criteria or userToken change
     */ 
    useEffect(() => {
        if(cookie.userToken){
            getUserOrders(cookie.userToken.token, setOrders, criteria);
            if (role === 'EMPLOYEE') setShowDeliverButton(true);
            else setShowDeliverButton(false);
        } else {
            removeCookie('userToken');
            history.push('/');
        }
    }, [refresh, criteria, cookie.userToken]);

    return (
        <div>
            <h1> ORDERS </h1>
            {orders.map((order) => {
                return (
                    <div key={order.id} className = 'Order'>
                        <Order order={order} />
                        {showDeliverButton && <button type='button' className='navButton' style={{ margin: 0 }} onClick={() => handleDeliver(order.id)}> Deliver order </button>}
                    </div>
                );
            })}
        </div>
        );
}

export default UserOrders;