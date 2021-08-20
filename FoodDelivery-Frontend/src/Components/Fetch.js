import { setRef } from "@material-ui/core"
/**
 * Function which calls /signUp endpoint from server, used in registering new user
 * @param {any} name - name of user
 * @param {any} surname - surname of user
 * @param {any} phoneNumber - user phone number
 * @param {any} email - user email (username)
 * @param {any} password - user password
 * @param {any} officeId - office ID in which employee will be working, null for clients
 * @param {any} roleName - name of role user will be having
 * @param {any} setResponse - function for setting server response
 */
export const signUp = (name, surname, phoneNumber, email, password, officeId, roleName, setResponse) => {
    const url = 'http://localhost:8080/api/UserController/signUp'
    const payload = {
        name: name,
        surname: surname,
        phoneNumber: phoneNumber,
        email: email,
        password: password,
        officeId: officeId,
        roleName: roleName
    }
    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }, body: JSON.stringify(payload)
    }
    fetch(url, options)
        .then(response => response.json())
        .then(result => {
                setResponse(result.response.message);
                return;
        })
}
/**
 * Function which calls /selfInformation endpoint from server, used for fetching information about logged user
 * @param {any} token - user token
 * @param {any} setUser - function for setting user information received from server
 */
export const fetchUser = (token, setUser) => {
    const url = 'http://localhost:8080/api/UserController/selfInformation';
    const options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'token': token,
        }
    };
    fetch(url, options)
        .then(response => response.json())
        .then(result => {
            setUser(result);
        })
}
/**
 * function that calls /users/{email} endpoint from server, used for fetching full user information by email
 * @param {any} userToken - user token containing email
 * @param {any} setUser - function for setting user information received from server
 */
export const fetchFullUser = (userToken, setUser) => {
    const url = 'http://localhost:8080/api/UserController/users/'+userToken.email;
    const options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'token': userToken.token,
        }
    };
    fetch(url, options)
        .then(response => response.json())
        .then(result => {
            setUser(result);
        })
}
/**
 * function that calls /offices endpoint used for fetching all offices from database
 * @param {any} setOffices - function for setting server response with offices
 */
export const fetchOffices = (setOffices) => {
    const url = 'http://localhost:8080/api/OfficeController/offices';
    let options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        }
    }
    fetch(url, options)
        .then(response => response.json())
        .then(result => {
            setOffices(result);
        })
}
/**
 * function which calls /verifyRole/{role} endpoint, used for verifying if user has access to certain resources
 * @param {any} token - user token
 * @param {any} role - role needed to access resource
 * @param {any} setAuthorized - function that sets server response
 */
export const verifyRole = (token, role, setAuthorized) => {
    const url = 'http://localhost:8080/api/UserController/verifyRole/' + role;
    let options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'token': token
        }
    }
    fetch(url, options)
        .then(response => response.json())
        .then(result => {
            if (result.response.message === 'Authorized')
                setAuthorized(true);
            else setAuthorized(false);
        })
}
/**
 * function which calls /dishes endpoint, used for fetching all dishes from database
 * @param {any} setDishes - function that sets server response with list of dishes
 */
export const getDishes = (setDishes) => {
    const url = 'http://localhost:8080/api/DishController/dishes';
    let options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        }
    }
    fetch(url, options)
        .then(response => response.json())
        .then(result => {
            result.map((dish)=>{
                dish.amount = 1;
            })
            console.log(result);
            setDishes(result);
        })
}
/**
 * function which calls /orders endpoint, fetching all orders from database
 * @param {any} token - user token
 * @param {any} setOrders - function that sets server response
 */
export const getAllOrders = (token, setOrders) => {
    const url = 'http://localhost:8080/api/OrderController/orders'
    let options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'token': token
        }
    }
    fetch(url, options)
        .then(response => response.json())
        .then(result => {
            setOrders(result);
        })
}
/**
 * function which calls /createDish endpoint, used for creating new dishes
 * @param {any} token - user token for verification
 * @param {any} name - dish name
 * @param {any} description - dish description
 * @param {any} price - dish price
 * @param {any} setResponse - function that sets server response
 * @param {any} setRefresh - function thats sets page refresh flag
 * @param {any} refresh - current page refresh flag value
 */
export const addNewDish = (token, name, description, price, setResponse, setRefresh, refresh) => {
    const url = 'http://localhost:8080/api/DishController/createDish';
    let payload = {
        name: name,
        description: description,
        price: price,
    }
    let options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'token': token
        }, body: JSON.stringify(payload)
    }
    fetch(url, options)
        .then(response => response.json())
        .then(result => {
            setResponse(result.response.message);
            setRefresh(!refresh);
            return;
        })
}
/**
 * function that calls /deleteDish/{id} endpoint, used for deleting dishes
 * @param {any} token - user token for veryfication
 * @param {any} key - id of dish
 * @param {any} setRefresh - function thats sets page refresh flag
 * @param {any} refresh - current page refresh flag value
 */
export const removeDish = (token, key, setRefresh, refresh) => {
    const url = 'http://localhost:8080/api/DishController/deleteDish/' + key;
    let options = {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'token': token
        }
    }
    fetch(url, options)
        .then(response => response.json())
        .then(result => {
            setRefresh(!refresh)
            return;
        })
}
/**
 * function which calls /createOffice endpoint, used for creating new office
 * @param {any} token - user token for verification
 * @param {any} city - office city
 * @param {any} street - office street
 * @param {any} postCode - office city post code
 * @param {any} phoneNumber - office phone number
 * @param {any} setRefresh - function thats sets page refresh flag
 * @param {any} refresh - current page refresh flag value
 * @param {any} setResponse - function that sets response from server
 */
export const addOffice = (token, city, street, postCode, phoneNumber, setRefresh, refresh, setResponse) => {
    const url = 'http://localhost:8080/api/OfficeController/createOffice';
    let payload = {
        city: city,
        street: street,
        postCode: postCode,
        phoneNumber: phoneNumber
    }
    let options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'token': token
        }, body: JSON.stringify(payload)
    }
    fetch(url, options)
        .then(response => response.json())
        .then(result => {
            setResponse(result.response.message);
            setRefresh(!refresh);
            return;
        })
}
/**
 * function that calls /deleteOffice/{id} endpoint, used for deleting office from database
 * @param {any} token - user token for verification
 * @param {any} key - id of office
 * @param {any} setRefresh - function thats sets page refresh flag
 * @param {any} refresh - current page refresh flag value
 */
export const removeOffice = (token, key, setRefresh, refresh) => {
    const url = 'http://localhost:8080/api/OfficeController/deleteOffice/' + key;
    let options = {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'token': token
        }
    }
    fetch(url, options)
        .then(response => response.json())
        .then(result => {
            setRefresh(!refresh)
            return;
        })
}
/**
 * function which calls /deleteOrder/{id} endpoint, used for deleting orders
 * @param {any} token - user token for verification
 * @param {any} key - id of order
 * @param {any} setRefresh - function thats sets page refresh flag
 * @param {any} refresh - current page refresh flag value
 */
export const removeOrder = (token, key, setRefresh, refresh) => {
    const url = 'http://localhost:8080/api/OrderController/deleteOrder/' + key;
    let options = {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'token': token
        }
    }
    fetch(url, options)
        .then(response => response.json())
        .then(result => {
            setRefresh(!refresh)
            return;
        })
}
/**
 * function which calls /ordersByRole/{criteria} endpoint, used for fetching orders based on user role (Client/Employee) and status of order (not delivered/delivered)
 * @param {any} token - user token for verification
 * @param {any} setOrders - function setting response from server
 * @param {any} criteria - true for delivered orders, false for active orders
 */
export const getUserOrders = (token, setOrders, criteria) => {
    const url = 'http://localhost:8080/api/OrderController/ordersByRole/'+criteria;
    let options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'token': token
        }
    }
    fetch(url, options)
        .then(response => response.json())
        .then(result => {
            console.log(result);
            setOrders(result);
        })
}
/**
 * function which calls /createOrder endpoint, used for creating new order
 * @param {any} token - user token for verification
 * @param {any} dishes - dishes to be included in order
 * @param {any} office - office that user choosed
 * @param {any} address - address that user provided for delivery
 * @param {any} setResponse - function that sets response from server
 */
export const addOrder = (token, dishes, office, address, setResponse) => {
    const url = 'http://localhost:8080/api/OrderController/createOrder';
    let payload = {
        dishes: dishes,
        office: office,
        address: address
    }
    let options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'token': token
        }, body: JSON.stringify(payload)
    }
    fetch(url, options)
        .then(response => response.json())
        .then(result => {
            setResponse(result.response.message);
            return;
        })
}
/**
 * function which calls /changePassword endpoint, used for changing password
 * @param {any} token - user token for verification
 * @param {any} oldPassword - old password for comparing to the one saved in database
 * @param {any} newPassword - new password to be saved
 * @param {any} setResponse - function that sets response from server
 */
export const changePassword = (token, oldPassword, newPassword, setResponse) => {
    const url = 'http://localhost:8080/api/UserController/changePassword';
    let payload = {
        oldPassword: oldPassword,
        newPassword: newPassword
    }
    let options = {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'token': token
        }, body: JSON.stringify(payload)
    }
    fetch(url, options)
        .then(response => response.json())
        .then(result => {
            setResponse(result.response.message);
            return;
        })
}
/**
 * function which calls /deliverOrder/{id}, used for setting order delivered flag to true
 * @param {any} token - user token for verification
 * @param {any} orderId - id of order
 */
export const deliverOrder = (token, orderId) => {
    const url = 'http://localhost:8080/api/OrderController/deliverOrder/' + orderId;
    let options = {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'token': token
        }
    }
    fetch(url, options)
        .then(response=>response.json())
        .then(result=>{
            return;
        })
}
/**
 * function which calls /employees endpoint, used for fetching list of all employees
 * @param {any} token - user token
 * @param {any} setEmployees - function that sets server response (list of employees)
 */
export const getEmployees = (token, setEmployees) => {
    const url = 'http://localhost:8080/api/UserController/employees';
    let options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'token': token
        }
    }
    fetch(url, options)
        .then(response=>response.json())
        .then(result=>{
            setEmployees(result);
        })
}
/**
 * function which calls /changeEmployeeStatus, used for setting employee flag "fired" to true of false
 * @param {any} token - user token for verification
 * @param {any} id - id of employee
 * @param {any} fired - fired flag, can be true or false
 * @param {any} setRefresh - function thats sets page refresh flag
 * @param {any} refresh - current page refresh flag value
 */
export const changeEmployeeStatus = (token, id, fired, setRefresh, refresh) => {
    const url = 'http://localhost:8080/api/UserController/changeEmployeeStatus';
    let payload = {
        id: id,
        fired: fired
    }
    let options = {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'token': token
        }, body: JSON.stringify(payload)
    }
    fetch(url, options)
        .then(response => response.json())
        .then(result => {
            setRefresh(!refresh)
            return;
        })
}
/**
 * function which calls /initialFill endpoint, used for filling database with initial data
 * */
export const initialFill = () => {
    const url = 'http://localhost:8080/api/DebugController/initialFill';
    let options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        }
    }
    fetch(url, options)
        .then(response=>response.json())
        .then(result => {
            return;
        })
}
/**
 * function that calls /checkIfDataExists endpoint, used for checking if database has data (checks if roles exists in database as it is the most important thing in database)
 * @param {any} setDataExists - function that sets database response
 * @param {any} setRefresh - function thats sets page refresh flag
 * @param {any} refresh - current page refresh flag value
 */
export const checkIfInitialDataExists = (setDataExists, setRefresh, refresh) => {
    const url = 'http://localhost:8080/api/DebugController/checkIfDataExists';
    let options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        }
    }
    fetch(url, options)
        .then(response => response.json())
        .then(result => {
            if (result.response.message === "false") setDataExists(false);
            else if (result.response.message === "true") setDataExists(true);
            setRefresh(!refresh);
            return;
        })
}