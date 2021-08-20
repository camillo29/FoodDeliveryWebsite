import employee from '../../Resources/employee.png';
import employeeFired from '../../Resources/employee_fired.png';
import email from '../../Resources/email.png'
import city from '../../Resources/city.png'
import phone from '../../Resources/phone.png'
import '../../App.css';
/**
 * Employee component that renders employee information
 * @param {any} props employee - employee information to be displayed
 */
const Employee = (props) => {
    return (
        <div>
            <div style = {{float: 'left', width: '50%'}}>
                <img className = 'EmployeeLogo' src = {props.employee.fired ? employeeFired : employee} />
            </div>
            <div style = {{float: 'right', width: '50%', textAlign: 'left'}}>
                <h3> <img src={employee} className = 'EmployeeIcon' /> {props.employee.name} {props.employee.surname} </h3>
                <h3> <img src={email} className='EmployeeIcon' /> {props.employee.email} </h3>
                <h3> <img src={phone} className='EmployeeIcon' /> {props.employee.phoneNumber} </h3>
                <h3> <img src={city} className='EmployeeIcon' /> {props.employee.street}, {props.employee.postCode} {props.employee.city} </h3>
                <h3> {props.employee.fired ? 'Employee is fired' : ''} </h3>
            </div>
        </div>
        );
}

export default Employee;