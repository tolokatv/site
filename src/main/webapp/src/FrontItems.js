import React, { Component } from "react";
import { Container, Row, Col } from 'reactstrap';
import Singlecard from "./Singlecard"
import './mycss/my.css'

export default class FrontItems extends Component {
    render() {
        return ( 
            <div>
                {this.props.items.map(element => <Singlecard sections={element} key={element.id} /> )}
            </div>
        );
    }
}