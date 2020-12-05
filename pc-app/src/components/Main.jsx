import React, { useState, useEffect } from "react";
import { PiDataService } from "../data-service/pi-data-service";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from 'react-bootstrap/Button'
import Container from "react-bootstrap/Container";



const Main = () => {

    const [pcStatus, setPcStatus] = useState(false);

    useEffect(() => {
        updateStatus();
    }, [pcStatus]);

    const turnOff = async () => {
        const res = await new PiDataService().turnOff();
        if (res === 'sleeping') {
            updateStatus();
        }
    }
    
    
    
    const pressButtonClick = async() => {
        const res = await new PiDataService().press();
    
        if (res) {
            updateStatus();
        } else {
            updateStatus();
        }
    }

    const updateStatus = async () => {
        const status = await new PiDataService().getSleepStatus();
        setPcStatus(!status);
    }

    return (
        <Container>
            <Row>
                {!pcStatus && (
                    <Col>
                        <Button onClick={pressButtonClick}>Turn On Pc</Button>
                    </Col>
                )}

                {pcStatus && (
                    <Col>
                        <Button onClick={turnOff}>Turn Off Pc</Button>
                    </Col>
                )}
            </Row>
        </Container>
    );



};



export default Main;