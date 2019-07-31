import React from 'react';
import Form from 'react-jsonschema-form';
import 'bootstrap/dist/css/bootstrap.min.css';
import {
    Card, CardImg, CardText, CardBody, CardLink,
    CardTitle, CardSubtitle, Container, Row, Col,
    Button
} from 'react-bootstrap';

class Singlecard extends React.Component {

    render() {
        return (
            <Card style={{ width: '18rem' }}>
                <Card.Img variant="top" src="https://loremflickr.com/100/100" />
                <Card.Body>
                    <Card.Title>{this.props.title}</Card.Title>
                    <Card.Text>
                        {this.props.item}
                    </Card.Text>
                    <Button variant="primary">{this.props.id}</Button>
                </Card.Body>
            </Card>
        )
    }
}

class Mycard extends React.Component {

    render() {
        return (
            <Container>
                <Row>
                    <Col sm={12} md={6} lg={4} xl={3}>
                        <Singlecard />
                    </Col>
                    <Col sm={12} md={6} lg={4} xl={3}>
                        <Singlecard />
                    </Col>
                    <Col sm={12} md={6} lg={4} xl={3}>
                        <Singlecard />
                    </Col>
                    <Col sm={12} md={6} lg={4} xl={3}>
                        <Singlecard />
                    </Col>
                </Row>
            </Container>
        );
    }
}
export default Mycard;