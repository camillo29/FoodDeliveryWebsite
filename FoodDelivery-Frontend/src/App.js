import React, {useState} from 'react';
import { useCookies } from 'react-cookie';
import NavBar from './Components/NavBar';
import Header from './Components/Header';
import MainPage from './Components/Pages/MainPage';
import NotFound from './Components/Pages/NotFound';
import About from './Components/Pages/About';
import Dishes from './Components/Pages/Dishes';

import Account from './Components/Account/Account';
import SignUp from './Components/Account/SignUp';


//ADMIN PAGES
import ManageDishes from './Components/AdminPages/ManageDishes';
import ManageOffices from './Components/AdminPages/ManageOffices';
import ManageOrders from './Components/AdminPages/ManageOrders';
import ManageEmployees from './Components/AdminPages/ManageEmployees';

//CLIENT PAGES
import MakeNewOrder from './Components/ClientPages/MakeNewOrder';
import UserOrders from './Components/ClientPages/UserOrders';

//CSS
import './App.css';

import {Route, Switch} from 'react-router-dom';
/**
 * Main component that renders website, uses React Router
 * */
function App(){
    const [cookie, setCookie, removeCookie] = useCookies(['userToken']);

    return (
        <div className = 'App'>
           <div className = 'Header'>
                <Header />
            </div>
            <div className = 'Container'>
                <div className = 'navBar'>
                    <NavBar />
                </div>
                <div className = 'Main'>
                    <Switch>
                        <Route exact path = "/" component = {MainPage} />
                        <Route path = '/about' >            <About/>                                    </Route>
                        <Route path = '/dishes'>            <Dishes/>                                   </Route>
                        <Route path = '/account'>           <Account/>                                  </Route>
                        <Route path = '/signUp'>            <SignUp/>                                   </Route>
                        <Route path = '/manageDishes'>      <ManageDishes/>                             </Route>
                        <Route path = '/manageOffices'>     <ManageOffices/>                            </Route>
                        <Route path = '/manageOrders'>      <ManageOrders/>                             </Route>
                        <Route path = '/manageEmployees'>   <ManageEmployees/>                          </Route>
                        <Route path = '/makeNewOrder'>      <MakeNewOrder/>                             </Route>
                        <Route path = '/userOrders'>        <UserOrders/>                               </Route>
                        <Route path = '*'>                  <NotFound/>                                 </Route>
                    </Switch>
                </div>
            </div>
            <div className = 'Footer'>
                <p> Author: Kamil Świątek (<a href= 'https://github.com/camillo29' style = {{color: 'white'}}>Github</a>) </p>
            </div>
        </div>
    );
}

export default App;