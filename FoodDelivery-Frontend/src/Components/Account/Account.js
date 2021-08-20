import { useState, useEffect } from 'react';
import { useCookies } from 'react-cookie';
import { fetchUser } from '../Fetch';
import { useHistory } from 'react-router-dom';
import ChangePasswordSection from './ChangePasswordSection';

import employee from '../../Resources/employee.png';
import phone from '../../Resources/phone.png';
import email from '../../Resources/email.png';
import city from '../../Resources/city.png';
import '../../App.css';
/**
 * Component that renders Account page allowing users to see information about their account
 */
const Account = () => {
	const [cookie, setCookie, removeCookie] = useCookies(['userToken'])
	const [user, setUser] = useState('');
	const [showChangePassword, setShowChangePassword] = useState(false);
	let history = useHistory();

	/**
	 * UseEffect which fetches user if logged in, removes userToken and redirects to main page if token has expired every userToken change
	 */
	useEffect(()=>{
		if(cookie.userToken)
			fetchUser(cookie.userToken.token, setUser);
		else {
			removeCookie('userToken');
			history.push('/');
			}
	}, [cookie.userToken])
	
	/**
	 * function which displays information about office if user is employee 
	 */
	const showOffice = () => {
		if (user.office) return (<h3><img src={city} className='EmployeeIcon' /> {user.office.street}, {user.office.postCode} {user.office.city} </h3>);
	}

	return (
			<div className='Account'>
				<h1> ACCOUNT </h1>
				<div style = {{float: 'right', width: '60%', textAlign: 'left', marginBottom: '5%'}}>
					<h3><img src = {employee} className = 'EmployeeIcon'/> {user.name} {user.surname}</h3>
					<h3><img src = {phone} className='EmployeeIcon' /> {user.phoneNumber}</h3>
					<h3><img src = {email} className='EmployeeIcon' /> {user.email}</h3>
					<h3>{showOffice()}</h3>
					<button type='button' className = 'navButton' style = {{float: 'left', margin: '0 auto'}} onClick={() => setShowChangePassword(!showChangePassword)}> {showChangePassword ? 'Cancel' : 'Change password'} </button>
				</div>
				<div>
					{showChangePassword && <ChangePasswordSection cookie={cookie} />}
				</div>
			</div>
		);
}

export default Account;