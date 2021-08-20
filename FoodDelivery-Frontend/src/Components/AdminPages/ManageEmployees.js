import { useState, useEffect } from 'react';
import { useCookies } from 'react-cookie';
import { useHistory } from 'react-router-dom';
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';

import { verifyRole, getEmployees, fetchOffices, signUp, changeEmployeeStatus } from '../Fetch';

import '../../App.css';
import Employee from '../Objects/Employee';
/**
 * Component that renders Manage employees page, allowing admin to register new employees and fire or hire back existing ones 
 */
const ManageEmployees = () => {
    const [cookie, setCookie, removeCookie] = useCookies(['userToken']);
    const [employees, setEmployees] = useState([]);

    const [name, setName] = useState('');
    const [surname, setSurname] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('zaq1@WSX');
    const [offices, setOffices] = useState([]);
    const [selectedOffice, setSelectedOffice] = useState('');
    const [role, setRole] = useState('EMPLOYEE');

    const [filterList, setFilterList] = useState(false);
    const [filterOffice, setFilterOffice] = useState(null);

    const [authorized, setAuthorized] = useState(false);

    const [error, setError] = useState('');
    const [response, setResponse] = useState('');
    const [refresh, setRefresh] = useState(false);
    let history = useHistory();
    /**
     * function which handles submitting new employee after checking if all requirements are fulfilled 
     */
    const handleSubmit = () => {
        if (name != '' && surname != '' && phoneNumber != '' && email != '' && selectedOffice != '') {
            signUp(name, surname, phoneNumber, email, password, selectedOffice.id, role, setResponse);
            setName('');
            setSurname('');
            setPhoneNumber('');
            setEmail('');
            setRefresh(!refresh);
            setError('');
        } else setError('All fields must be filled');
    }
    /**
     * function which handles changing employee status
     * @param {any} employee employee which status will be changed
     */
    const handleEmployeeState = (employee) => {
        changeEmployeeStatus(cookie.userToken.token, employee.id, !employee.fired, setRefresh, refresh);
    }
    /**
     * UseEffect that gets all employees if admin was verified and removes userToken if it expired every refresh and userToken change
     */
    useEffect(()=>{
        if (cookie.userToken)
            verifyRole(cookie.userToken.token, "ADMIN", setAuthorized);
        else {
            removeCookie('userToken');
            history.push('/');
            return;
            }
        if(!filterOffice)
            getEmployees(cookie.userToken.token, setEmployees);
        else{
            var filteredEmployees = employees.filter(employee => employee.city === filterOffice.city);
            setEmployees(filteredEmployees);
        }
        fetchOffices(setOffices);
    }, [refresh, filterOffice, cookie.userToken])

    if(!authorized) return <h1> NOT AUTHORIZED </h1>

    else return (
        <div>
        <h1>MANAGE EMPLOYEES</h1>
            <div className = 'LeftPanel'>
                <Autocomplete
                    options={offices}
                    getOptionLabel={(option) => option.city + ', ' + option.street + ' ' + option.postCode}
                    style={{ width: '60%', margin: '0 auto' }}
                    onChange={(event, value) => setFilterOffice(value)}
                    renderInput={(params) =>
                        <TextField {...params} label="Filter by office" variant="outlined" />}
                />
            {employees.map((employee)=>{
                return (
                    <div key = {employee.id} className = 'Employee'>
                        <Employee employee = {employee} />
                        <button type = 'button' className = 'navButton' style = {{margin: 0, marginBottom: '5px'}} onClick = {() => handleEmployeeState(employee)}>{employee.fired ? "Hire employee back" : "Fire"}</button>
                    </div>
                    );
            })}
            </div>
            <div className = 'RightPanel'>
                <p> REGISTER NEW EMPLOYEE </p>
                <form>
                    <ul className = 'NoBulletList'>
                        <label className='Label'>Name</label><input type='text' className='Input' style={{ width: '50%' }} value = {name} onChange = {(e) => setName(e.target.value)}/>
                        <label className='Label'>Surname</label><input type='text' className='Input' style={{ width: '50%' }} value={surname} onChange={(e) => setSurname(e.target.value)} />
                        <label className='Label'>Phone number</label><input type='text' className='Input' style={{ width: '50%' }} value={phoneNumber} onChange={(e) => setPhoneNumber(e.target.value)} />
                        <label className='Label'>Email</label><input type='text' className='Input' style={{width:'50%'}} value={email} onChange={(e) => setEmail(e.target.value)} />
                    
                        <Autocomplete
                            options={offices}
                            getOptionLabel={(option) => option.city + ', ' + option.street + ' ' + option.postCode}
                            style={{ width: '60%', margin: '0 auto' }}
                            onChange={(event, value) => setSelectedOffice(value)}
                            renderInput={(params) =>
                                <TextField {...params} label="Office" variant="outlined" />}
                        />
                        <li><button type = 'button' className = 'navButton' style = {{marginTop: '5px'}} onClick = {()=>handleSubmit()}> Submit </button></li>
                    </ul>
                    <div className = 'Error'> {error} </div>
                    <div> {response} </div>
                    <p>Default user password is: zaq1@WSX</p>
                </form>
            </div>
        </div>
        );
}

export default ManageEmployees;

        /*
            private String name;

    private String surname;

    private String phoneNumber;

    private String email;

    private String password;

    private Long officeId;

    private String roleName;
        */