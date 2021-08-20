import React, { useState, useEffect } from 'react';
import { fetchOffices } from '../Fetch';
import Office from '../Objects/Office';

import '../../App.css';
/**
 * Component which renders About page, diplaying all offices to user
 */
const About = () => {
    const [offices, setOffices] = useState([]);
    /**
     * UseEffect that fetches offices on initial render
     */
    useEffect(()=>{
        fetchOffices(setOffices);
    }, [])

    return (
            <div> 
                <h1> OUR OFFICES </h1> 
                <div className = 'Offices'>
                    {offices.map((office) => {
                        return (
                            <div className='Office'>
                                <Office key={office.id} office={office} />
                            </div>
                        );
                    })}
                </div>
            </div>
        );
}
export default About;