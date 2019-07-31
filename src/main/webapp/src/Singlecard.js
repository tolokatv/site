import React, { Component } from "react";
// import { Container, Row, Col } from 'reactstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import {
    Card, CardImg, CardText, CardBody, CardLink,
    CardTitle, CardSubtitle, Container, Row, Col,
    Button
} from 'reactstrap';
import './mycss/my.css'

export default class Singlecard extends Component {

    render() {
        return (
            <Col sm={12} md={6} lg={4} xl={3}>
                <Card style={{ width: '18rem' }}>
                    <CardImg variant="top" src="https://loremflickr.com/100/100" />
                    <CardBody>
                        <CardTitle>{this.props.sections.id}</CardTitle>
                        <CardText>
                            {this.props.sections.id}
                        </CardText>
                        <Button variant="primary">{this.props.sections.id}</Button>
                    </CardBody>
                </Card>
            </Col>
        );
    }
}
