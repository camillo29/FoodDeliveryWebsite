import React, {useEffect, useState} from 'react';
import SignIn from './SignIn';
import { useCookies } from 'react-cookie';
import { Link, useHistory} from 'react-router-dom';
/**
 * Component which renders signing section with content decided by wheter user is logged in or not
 */
const SigningSection = () => {
    const [cookie, setCookie, removeCookie] = useCookies(['userToken']);
    let history = useHistory();

    /**
     * UseEffect which refreshes this section every userToken change
     */
    useEffect(()=>{
        getSection();
    }, [cookie.userToken])

    /**
     * function which handles loggin out by removing token and redirecting to home page 
     */
    const HandleLogOut = () => {
        removeCookie('userToken');
        history.push('/');
        return;
    }

    /**
     * function that returns section based on wheter user is logged in or not
     * Logged in - section with Account button and Sing out button
     * Not logged - section with Sign in form and Sign up button
     */
    const getSection = () => {
        if (!cookie.hasOwnProperty('userToken')) {
            return (
                <div>
                    <SignIn />
                    <ul style={{ listStyleType: 'None' }}>
                        <li><Link to = "/signUp" className = 'navButton'> SIGN UP </Link></li>
                    </ul>
                </div>
             );
        }
        else return (
            <div>
                <p style = {{textAlign: 'center'}}> Welcome <h4>{cookie.userToken.email}</h4> </p>
                <ul style={{ listStyleType: 'None' }}>
                    <li><Link to="/account" className="navButton">ACCOUNT</Link></li>
                    <li><button type='button' className='navButton' onClick={() => HandleLogOut()}>SIGN OUT</button></li>
                </ul>
            </div>
        );
    }
    
    return (
        <div>
            {getSection()}
        </div>
    );
}

export default SigningSection;