import banner from '../Resources/banner.png';
/**
 * Component which renders header section
 */
const Header = () => {
	return (
		<div>
			<img src = {banner} style = {{objectFit:'contain', borderRadius: '50px'}} alt = 'FOOD DELIVERY'/>
		</div>
		);
}
export default Header;