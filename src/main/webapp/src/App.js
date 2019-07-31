import React from 'react';
import { Container, Row, Col } from 'reactstrap';
import './App.css';
import './mycss/my.css'
// import './newspaper/style.css'

// import Myform from './Myform'
// import Mycard from './Mycard'
import Mynavbar from './Navbar'
// import Myjumbotron from './Jumbotron'
// import HeroArea from './HeroArea'
// import Myslider from './Myslider'
// import VerticalStringSlider from "./VerticalStringSlider"
// import Myheaderarea from './Myheaderarea'
import BlockTheme from "./BlockTheme"

// function App() {
export default class App extends React.Component {
  state = {
    todos: []
  }

  componentDidMount() {
    // fetch('http://jsonplaceholder.typicode.com/todos')
    // .then(res => res.json())
    // .then((data) => {
    const data =
      [ 
        {
          sectiontitle: 'Економіка',
          sections: [
            {
              id: 234,
              title: 'Перший заголовок 1',
              item: '1 - Lorem ipsum dolor sit amet.',
            },
            {
              id: 235,
              title: 'Другий заголовок 2',
              item: '2 - Lorem ipsum dolor sit amet.',
            }
          ]
        },
        {
          sectiontitle: 'Політика',
          sections: [
            {
              id: 244,
              title: 'Перший заголовок 1',
              item: '1 - Lorem ipsum dolor sit amet.',
            },
            {
              id: 245,
              title: 'Другий заголовок 2',
              item: '2 - Lorem ipsum dolor sit amet.',
            }
          ]
        }
      ];
    
    const slidertext = [
      {
        text: '1 - Lorem ipsum dolor sit amet.',
        addr: 'http://1.toloka.kiev.ua/',
      },
      {
        text: '2 - Lorem ipsum dolor sit amet.',
        addr: 'http://2.toloka.kiev.ua/',
      },
      {
        text: '3 - Lorem ipsum dolor sit amet.',
        addr: 'http://3.toloka.kiev.ua/'
      },
      {
        text: '4 - Lorem ipsum dolor sit amet.',
        addr: 'http://4.toloka.kiev.ua/'
      },
      {
        text: '5 - Lorem ipsum dolor sit amet.',
        addr: 'http://5.toloka.kiev.ua/'
      },
      {
        text: '6 - Lorem ipsum dolor sit amet.',
        addr: 'http://6.toloka.kiev.ua/'
      },
      {
        text: '7 - Lorem ipsum dolor sit amet.',
        addr: 'http://7.toloka.kiev.ua/'
      }
    ];
    
    // this.setState({ slidertext: slidertext });
    this.setState({ todos: data });
    // console.log(this.state.todos);
    // console.log(this.state.slidertext);
  }
//   )
//     .catch(console.log)
// }
// =======================

render() {
  return (
    <div className="App">
      {/* NavBar Area */}
      <Mynavbar />
      {/* Слайдер з новинами */}
      {/* <VerticalStringSlider intstring={this.state.slidertext}/> */}
      {/* Header Area  */}
      {/* <Myheaderarea/> */}

   {/*  */}
      {/* <!-- ##### Hero Area Start ##### --> */}
      <BlockTheme info={this.state.todos}/>
      {/* <HeroArea /> */}
      {/*  */}
      {/* <Myslider /> */}
      {/* <h1>Тестуємо Реакт</h1> */}
      {/* <Myform /> */}
      {/* <Myjumbotron /> */}
      {/* <Mycard /> */}
    </div>
  );
}
}

// export default App;
