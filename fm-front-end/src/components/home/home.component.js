import React, { Component } from "react";
import axios from 'axios';
import Jumbotron from 'react-bootstrap/Jumbotron'
import Container from 'react-bootstrap/Container'
import Card from 'react-bootstrap/Card'
import Button from 'react-bootstrap/Button'
import { Link } from "react-router-dom";


export default class Home extends Component {

    state = {};


    componentDidMount() {

        axios.get('auth/user').then(

            res => {
                this.setState({
                    user: res.data
                });

            }
        ).catch(
            err => {
                console.log(err);
            }
        );

    }
    
    render() {

        if (this.props.user) {
            return (

                <Jumbotron jumbotron-fluid>
                    <Container>
                        <h2>Hi {this.props.user.name} </h2>
                        <p>
                            Bem vindo ao Financials Manager, vamos começar ?.
                </p>
                    </Container>
                </Jumbotron>

            )
        } else {
            return (

                <div className="col-12">
                    <Jumbotron className="mt-5" jumbotron-fluid>
                        <Container>
                            <h1>Olá,</h1><h3>Bem vindo ao Financials Manager, vamos começar ?</h3>
                        </Container>
                    </Jumbotron>
                    <div className="row-flex">
                        <Card>
                            <Card.Header>Gerencia suas finanças</Card.Header>
                            <Card.Body>
                                <Card.Title>Rápido, fácil e pratico.</Card.Title>
                                <Card.Text>
                                Cadastre-se e mude a maneira de gerenciar suas finanças, sem complicações.
                           </Card.Text>
                                <Link to="/register"> <Button variant="primary">Ir agora</Button></Link>
                            </Card.Body>
                        </Card>
                    </div>
                </div>

            )

        }


    }
}