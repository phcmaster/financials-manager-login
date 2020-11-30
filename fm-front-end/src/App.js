import React, { Component } from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css'
import './App.css'
import Home from './components/home.component'
import Nav from './components/nav.component'
import Login from './components/login.component'
import Register from './components/register.component'
import axios from 'axios';
import { Forgot } from './components/forgot.component';
import { Otp } from './components/otp.component';
import { ChangePassword } from './components/changePassword.component';


export default class App extends Component {

  state = {};

  componentDidMount = () => {

    axios.get('auth/user').then(

        res => {
               this.setUser(res.data);
        
        }
    ).catch(
        err => {
            console.log(err);
        }
    );

};


setUser = user => {
  this.setState({
      user: user
  });

};


  render(){
      return (

      <BrowserRouter>
        <div className="App">
            <Nav user={this.state.user} setUser={this.setUser} />

          <div className="auth-wrapper">
            <div className="auth-inner">
            
              <Switch>
                <Route exact path="/" component={() => <Home user={this.state.user} />} />
                <Route exact path="/login" component={() => <Login setUser={this.setUser} />} />
                <Route exact path="/register" component={Register} />
                <Route exact path="/forgotPassword" component={Forgot} />
                <Route exact path="/otp" component={() => <Otp user={this.state.user} />} />
                <Route exact path="/change-password" component={ChangePassword} />
              </Switch>

            </div>
          </div>

        </div>
    </BrowserRouter>
        
      );

}
}