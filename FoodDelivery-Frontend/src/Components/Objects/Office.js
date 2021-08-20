import React from 'react';
import '../../App.css';
import city from '../../Resources/city.png';
import phone from '../../Resources/phone.png';
import street from '../../Resources/street.png';
/**
 * Office component that renders office information
 * @param {any} props office - office information to be displayed
 */
const Office = (props) => {
    return (
            <div className = 'OfficeWrap'>
                <h2> 
                    <img src = {city} className = 'OfficeIcon' />   {props.office.city} 
                </h2>
                <h3> 
                    <img src={street} className='OfficeIcon' />   {props.office.street}, {props.office.postCode} </h3>
                <h3>
                    <img src={phone} className='OfficeIcon' />   {props.office.phoneNumber}
                </h3>
            </div>
        );
}

export default Office;