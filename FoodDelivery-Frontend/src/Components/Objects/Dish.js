import '../../App.css'
/**
 * Dish component that renders dish information
 * @param {any} props 
 *          forOrder - flag upon which content is decided
 *          dish - dish information to be displayed
 *          amount - amount of dish ordered
 */
const Dish = (props) => {
    if (!props.forOrder || props.forOrder === false)
        return (
            <div>
                <h1> {props.dish.name} {props.amount >= 1 ? 'x' + props.amount : ''} </h1>
                <h4> {props.dish.description} </h4>
                <p> {props.dish.price} PLN </p>
            </div>
        );
    else if (props.forOrder && props.forOrder === true)
        return (
            <div>
                <div>
                    <h3 style={{ textAlign: 'left', marginLeft:'2px'}}> {props.dish.name} {props.amount >= 1 ? 'x' + props.amount : ''} = {Math.floor(props.dish.price * props.amount * 100)/100} PLN </h3>
                </div>

            </div>
            );
}

export default Dish;