import React, { Component } from "react";
import { Container, Row, Col } from 'reactstrap';
import FrontItems from "./FrontItems"
import './mycss/my.css'

export default class Block extends Component {
    render() {
        return (
            <Container>
                <Row>
                    <div className="block-name">
                        {this.props.sections.sectiontitle}
                    </div>
                </Row>
                <Row>
                    <FrontItems items={this.props.sections.sections} key={Math.random()} />
                </Row>
            </Container>
        );
    }
}






