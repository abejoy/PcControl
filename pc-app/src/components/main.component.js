import React from "react";
import {Component} from 'react';
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from 'react-bootstrap/Button'
import {PiDataService} from "../data-service/pi-data-service";

export default class Main extends Component {

    render() {
        return (
            <Container>
                <Row>
                    <Col>
                        <Button onClick={() => this.pressButtonClick()}>press</Button>
                    </Col>
                </Row>
            </Container>
        )
    }

    pressButtonClick() {
        new PiDataService().press().then(ret => {
            console.log(ret)
        }).catch(err=>{
            console.log(err)
        })
    }
}