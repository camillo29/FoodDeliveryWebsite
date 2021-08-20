import { useState, useEffect } from 'react';
import { initialFill, checkIfInitialDataExists} from '../Fetch';

import image from '../../Resources/mainPageImage.png'
/**
 * Component that renders Main Page of website
 */
const MainPage = () => {
    const [dataExists, setDataExists] = useState();
    const [refresh, setRefresh] = useState(false);
    /**
     * function that handles initial fill button
     */
    const handleInitialFill = () => {
        initialFill();
    }
    /**
     * function returning different content based on wheter data exists in database or not
     */
    const decideContent = () => {
        if (dataExists === true)
            return (
                <>
                    <div>
                        <ul>
                            <li>ADMIN
                            "email":"jan_kowalski@op.pl",
                            "password":"zaq1@WSX"
                             </li>
                            <li>EMPLOYEE
                            "email":"krzysztof_janowski@op.pl",
                            "password":"zaq1@WSX"
                             </li>
                            <li>CLIENT
                            "email":"piotr_drewno@op.pl",
                            "password":"zaq1@WSX"
                             </li>
                        </ul>
                    </div>
                </>
            );
        else if(dataExists === false)
            return(
                <div>
                    <h3> DEBUG </h3>
                    <button type='button' onClick = {()=>handleInitialFill()}> Fill database with initial data </button>
                </div>
            );
        }
    /**
     * UseEffect that checks if initial data exists every refresh flag change
     */
    useEffect(()=>{
        checkIfInitialDataExists(setDataExists, setRefresh, refresh);
    }, [refresh])
    
    return (
        <div>
            <h2> Welcome to our website, the best website for ordering food delivieries! </h2>
            {decideContent()}
            <div>
                <img src = {image} style = {{maxWidth: '50%', marginTop: '3%'}}/>
            </div>
        </div>
        );
}

export default MainPage;

// <img src={image} style={{ margin: '5%', maxWidth: '60%', maxHeight: '50%' }} />