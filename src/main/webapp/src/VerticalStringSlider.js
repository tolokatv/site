// ==============================
// документация на слайдер
// https://github.com/akiran/react-slick
//
// ==============================

import React, { Component } from "react";
import Slider from "./slick/slider"
import './slick/slick.css'
import { Container, Row, Col } from 'reactstrap';

export default class VerticalStringSlider extends Component {

  render() {
    const settings = {
      autoplay: true,
      dots: false,
      arrows: false,
      infinite: true,
      slidesToShow: 1,
      slidesToScroll: 1,
      vertical: true,
      verticalSwiping: true,
      speed: 500,
      autoplaySpeed: 2000,
      beforeChange: function (currentSlide, nextSlide) {
        console.log("before change", currentSlide, nextSlide);
      },
      afterChange: function (currentSlide) {
        console.log("after change", currentSlide);
      }
    };
    return (
      <div className="hero-area">
        <Container>
          <Row className="items-left" sm="12" lg="4">
          
            {/* <!-- Breaking News Widget --> */}
            <Col sm="12" lg="3">
              <p className="hero-name">Горячі Новини   {this.props.slidertext}</p>
            </Col>
            <Col sm="12" lg="9" className="hero-text">
              <Slider {...settings} >
                <div>
                  <h3>1111111111 222222222 33333333 444444444 5555555</h3>
                </div>
                <div>
                  <h3>2</h3>
                </div>
                <div>
                  <h3>3</h3>
                </div>
                <div>
                  <h3>4</h3>
                </div>
                <div>
                  <h3>5</h3>
                </div>
                <div>
                  <h3>6</h3>
                </div>
              </Slider>
            </Col>
          </Row>
        </Container>
      </div>

    );
  }
}
