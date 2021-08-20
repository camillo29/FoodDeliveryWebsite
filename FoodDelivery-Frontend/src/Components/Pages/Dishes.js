import { useState, useEffect } from 'react';
import { getDishes } from '../Fetch';
import Dish from '../Objects/Dish';
import '../../App.css'
/**
 * Component renders dishes page, diplaying all avaiable dishes
 */
const Dishes = () => {
    const [dishes, setDishes] = useState([]);

    /**
     * UseEffect that fetches dishes on initial render
     */
    useEffect(()=>{
        getDishes(setDishes);
    }, [])

    return (
        <div>
            {dishes.map((dish)=>{
                return (
                    <div key={dish.id} className='Dish' style = {{marginLeft: '20%', marginRight:'20%'}}>
                        <Dish dish={dish} />
                    </div>
                );
            })}
        </div>
    );
}

export default Dishes;