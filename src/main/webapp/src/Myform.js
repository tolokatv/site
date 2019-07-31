import React from 'react';
import Form from 'react-jsonschema-form';
import 'bootstrap/dist/css/bootstrap.min.css';
// import { Button } from 'reactstrap';
import {
  Container, Row, Col
} from 'react-bootstrap';

const mySchema = {
  "title": "Заголовок форми",
  "description": "Тестуємо динамічне створення форми",
  "type": "object",
  "required": [
    "name"
  ],
  "properties": {
    "name": {
      "type": "string",
      "title": "Name"
    },
    "phone": {
      "type": "string",
      "title": "Phone"
    }
  }
};

class Myform extends React.Component {

  constructor(props) {
    super(props);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleSubmit({ formData }) {
    console.log("Submit");
    console.log(formData);
  }

  render() {
    return (
      <Container>
        <Row>
          <Col sm={12} md={6} lg={4} xl={3}>
            <Form schema={mySchema}
              onSubmit={this.handleSubmit}
            />
          </Col>
        </Row>
      </Container>
    );
  }

}

export default Myform;