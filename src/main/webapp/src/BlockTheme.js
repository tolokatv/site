// Тематический блок на первую страницу
import React, { Component } from "react";
import { Container, Row, Col } from 'reactstrap';
import Block from "./Block"
import './mycss/my.css'

export default class BlockTheme extends Component {
    render() {
        var myblocks = [];

        this.props.info.forEach(element => {
            myblocks.push(<Block sections={element} key={Math.random()} />);
            console.log("BlockTheme myblocks element");
            console.log(element);
            console.log(myblocks);
            // key={Math.random()}
        }
        );
        console.log("BlockTheme myblocks");
        console.log(myblocks);

        return (
            // {console.log(myblocks)} 
            // " "
            <div>{myblocks}</div>

            // {
            //     myblocks.forEach(element => {
            //         <Block
            //             section={element}
            //             key={Math.random()} />
            //     }
            //     )
            // }
        );
    }
}
