import Dish from './Dish';
/**
 * Order component that renders order information including dishes
 * @param {any} props order - order information to be displayed
 */
const Order = (props) => {
	return (
		<div>
			<h2> Receipt </h2>
			{props.order.orderDishes.map((dish)=> {
				return <Dish key = {dish.id} dish = {dish.dish} amount = {dish.amount} forOrder = {true}/>
			})}
			<div style={{ textAlign: 'left', margin:'2px'}}>
				<p >__________________________________</p>
				<h3> Total price = {Math.floor(props.order.totalPrice*100)/100} PLN</h3>
				<p >__________________________________</p>
				<h3> Address: {props.order.address} </h3>
				<h3> Client: {props.order.client.person.name} {props.order.client.person.surname} </h3>
				<h3> Employee: {props.order.employee.person.name} {props.order.employee.person.surname} </h3>
				<h2 style = {{textAlign: 'center'}}> {props.order.delivered ? "Delivered" : ''} </h2>
			</div>
		</div>	
	);
}

export default Order;