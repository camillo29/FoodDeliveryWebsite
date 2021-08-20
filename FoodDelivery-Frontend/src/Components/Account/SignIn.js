import React, {useState} from 'react';
import { useCookies } from 'react-cookie';
import '../../App.css';

/**
 *  Component that renders Sign in form allowing user to log into website 
 */
const SignIn = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [cookie, setCookie, removeCookie] = useCookies(['userToken']);
    /**
     *  function handling submitting user information 
     */
    const handleSubmit = () => {
        if ((email && password) && (email !== '' && password !== ''))
            signIn();
        else setError('All fields must be filled');
    }
    /**
     * function which calls /signIn endpoint, used for signining user into website 
     */
    const signIn = () => {
        let payload = {
            email: email,
            password: password
        }
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }, body: JSON.stringify(payload),
        };

        const url = 'http://localhost:8080/api/UserController/signIn';
        let dt = new Date();
        dt.setMinutes(dt.getMinutes() + 15);            //ORIGINAL
        //dt.setMinutes(dt.getMinutes() + 1);           //DEBUG

        fetch(url, options)
            .then(response => response.json())
            .then(result => {
                if (result.hasOwnProperty('response')) setError(result.response.message);
                else {
                    setCookie('userToken', { token: result.token, email: result.email}, { expires: dt, path: '/' })
                }
            });

    }

    return (
            <div>
                <form>
                    <ul style={{ listStyleType: 'None' }}>
                            EMAIL
                        <li><input type = 'email' onChange = {(e) => setEmail(e.target.value)}/></li>
                            PASSWORD
                        <li><input type = 'password' onChange = {(e) => setPassword(e.target.value)}/></li>
                    </ul>
                    <ul style={{ listStyleType: 'None' }}>
                        <li><button type = 'button' className = 'navButton' onClick = {()=>handleSubmit()}> SIGN IN </button></li>
                        <div className='Error'> {error} </div>
                    </ul>
                    
                </form>
            </div>
        );
}

export default SignIn;