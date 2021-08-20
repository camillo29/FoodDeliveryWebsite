import React, { useState } from 'react';
import { signUp } from '../Fetch';
import '../../App.css';
/**
 * Component that renders Sign up page, allowing user to register new account 
 */
const SignUp = () => {
    const [name, setName] = useState('');
    const [surname, setSurname] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [repeatPassword, setRepeatPassword] = useState('');
    const [roleName, setRoleName] = useState('CLIENT');

    const [error, setError] = useState('');
    const [response, setResponse] = useState('');

    /**
     * function which handles submitting form and calls signUp from Fetch if all requirements have been fulfilled
     */
	const HandleSignUp = () => {
        setError('');
        if (name != '' && surname != '' && phoneNumber != '' && email != '' && password != '' && repeatPassword != '') {
            if(password == repeatPassword){
                signUp(name, surname, phoneNumber, email, password, null, roleName, setResponse);
                setName('');
                setSurname('');
                setPhoneNumber('');
                setPassword('');
                setRepeatPassword('');
                setEmail('');
            }
            else setError('Passwords doesnt match')
                
        } else setError('All fields must be filled');
        return;
	}

	return (
        <div className = 'Account'>
		    <h1> SIGN UP </h1>
            <div style = {{float: 'right', width: '85%'}}>
                <form> 
                    <label className='Label'> Name: </label> <input type = "text" value = {name} className = 'Input' onChange = {(e) => setName(e.target.value)} />
                    <label className='Label'> Surname: </label> <input type="text" value = {surname} className='Input' onChange={(e) => setSurname(e.target.value)} />
                    <label className='Label'> Phone number: </label> <input type="number" value = {phoneNumber} className='Input' onChange={(e) => setPhoneNumber(e.target.value)} />
                    <label className='Label'> Email: </label> <input type="email" value = {email} className='Input' onChange={(e) => setEmail(e.target.value)} />
                    <label className='Label'> Password: </label> <input type="password" value = {password} className='Input' onChange={(e) => setPassword(e.target.value)} />
                    <label className='Label'> Repeat password: </label> <input type="password" value = {repeatPassword} className='Input' onChange={(e) => setRepeatPassword(e.target.value)} />
                    <div style = {{width: '72%'}}>
                        <button type='button'  className = 'navButton' onClick = {()=>HandleSignUp()}> SUBMIT </button>
                    </div>
                    <div style = {{width: '80%'}}>
                        <h1 className='Error'> {error} </h1>
                        <h1> {response} </h1>
                    </div>
                    
                </form>
            </div>
        </div>
		);
}

export default SignUp;