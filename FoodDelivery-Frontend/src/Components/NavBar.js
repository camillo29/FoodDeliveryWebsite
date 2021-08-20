import React, {useState, useEffect} from 'react';
import { useCookies } from 'react-cookie';
import { Link } from 'react-router-dom';
import { fetchFullUser } from './Fetch';
import SigningSection from './Account/SigningSection';
import '../App.css';
/**
 * Navigation bar component renders buttons that are used to navigate website
 * */
const NavBar = () => {
    const [user, setUser] = useState('');
    const [cookie, setCookie, removeCookie] = useCookies(['userToken']);
    /**
     * Function that returns navigation bar content (buttons) based on user roles
     * Client gets active and previous orders pages
     * Employee gest active orders page he/she is assigned to
     * Admin get pages for managing entities (Dishes, offices, orders and employees)
     * */
    const decideContentBasedOnRole = () => {
        if (user !== '') {
            return (
                user.roles.map((role) => {
                    if (role.name === 'CLIENT') {
                        return (
                            <>
                                <li><Link to='/makeNewOrder' className = 'navButton'> MAKE NEW ORDER </Link> </li>
                                <li><Link to={{ pathname: "/userOrders", state: { role: "CLIENT", criteria: false }}} className='navButton'> MY ORDERS </Link> </li>
                                <li><Link to={{ pathname: "/userOrders", state: { role: "CLIENT", criteria: true }}} className='navButton'> PREVIOUS ORDERS </Link> </li>
                            </>
                        );
                    }
                    else if (role.name === 'EMPLOYEE') {
                        return (
                            <>
                                <li> <Link to={{pathname: "/userOrders", state: {role: "EMPLOYEE", criteria: false}}} className='navButton'> ORDERS TO DELIVER </Link> </li>
                            </>
                        );
                    }
                    else if (role.name === 'ADMIN') {
                        return (
                            <>
                                <li> <Link to = '/manageDishes' className = 'navButton'> MANAGE DISHES </Link> </li>
                                <li> <Link to = '/manageOffices' className = 'navButton'> MANAGE OFFICES </Link> </li>
                                <li> <Link to = '/manageOrders' className = 'navButton'> MANAGE ORDERS </Link> </li>
                                <li> <Link to = '/manageEmployees' className = 'navButton'> MANAGE EMPLOYEES </Link> </li>
                            </>
                            );
                    }
                })
            );
        }
        else return '';
    }

    /**
     * UseEffect that fetches user or sets user to null every time cookie changes
     */
    useEffect(()=>{
        if (cookie.userToken)
            fetchFullUser(cookie.userToken, setUser);
        else setUser('');
    }, [cookie])

    return (
        <nav>
            <SigningSection/>
            <ul className = 'NoBulletList'>
                <li><Link to = '/' className = 'navButton'> HOME </Link></li>
                <li><Link to='/about' className='navButton'> ABOUT </Link></li>
                <li><Link to='/dishes' className = 'navButton'>MENU </Link> </li>
                {decideContentBasedOnRole()}
            </ul>
        </nav>
        );
}

export default NavBar;