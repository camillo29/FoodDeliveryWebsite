import { useState, useEffect } from 'react';
import { changePassword } from '../Fetch';
import { useHistory } from 'react-router-dom';
import '../../App.css'
/**
 * Component which renders change password section on account page, allows users to change their password
 * @param {any} props
 *              cookie - cookie with userToken
 *              
 */
const ChangePasswordSection = (props) => {
    const [oldPassword, setOldPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [repeatPassword, setRepeatPassword] = useState('');
    let history = useHistory();

    const [error, setError] = useState('');
    const [response, setResponse] = useState('');
    /**
     * function which handles submitting data, and calls changePassword from Fetch after checking if all requirements have been fulfilled 
     */
    const handleSubmit = () => {
        if (oldPassword && oldPassword !== '' && newPassword && newPassword !== '' && repeatPassword && repeatPassword !== ''){
            changePassword(props.cookie.userToken.token, oldPassword, newPassword, setResponse);
            setOldPassword('');
            setNewPassword('');
            setRepeatPassword('');
            setError('');
        } else {
            setError('All fields must be filled!');
            setResponse('');
        }
    }

    /**
     * UseEffect which redirects to Main Page if user token expired or have been removed every userToken change
     */ 
    useEffect(()=>{
        if (!props.cookie.userToken){
            history.push('/');
        }
    }, [props.cookie.userToken])

    return (
        <div>
            <form style={{ float: 'right', width: '85%', textAlign: 'left'}}>
                <label className='Label'> Old password:     </label><input type = 'password' className = 'Input' value = {oldPassword} onChange = {(e)=>setOldPassword(e.target.value)} />
                <label className='Label'> New password:     </label><input type = 'password' className = 'Input' value = {newPassword} onChange = {(e)=>setNewPassword(e.target.value)} />
                <label className='Label'> Repeat password:  </label><input type = 'password' className = 'Input' value = {repeatPassword} onChange = {(e)=>setRepeatPassword(e.target.value)} />
                <button type='button' className='navButton' style={{ float: 'left', margin: '0 auto' }} onClick={() => handleSubmit()}> Submit </button>
                <div className='Error' style = {{float: 'left', marginLeft: '5px'}}> {error} </div>
                <div style = {{float: 'left'}}> {response} </div>
            </form>
        </div>
    );
}

export default ChangePasswordSection;