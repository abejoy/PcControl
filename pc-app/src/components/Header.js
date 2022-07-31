import React from 'react';

const Header = props => {

    if(props.data){
      var name = props.data.name;
      var description= props.data.description;
      var networks= props.data.social.map(function(network){
        return <li key={network.name}><a href={network.url}><i className={network.className}></i></a></li>
      })
    }

   return (
      <header id="home">

      <nav id="nav-wrap">

         <a className="mobile-btn" href="#nav-wrap" title="Show navigation">Show navigation</a>
         <a className="mobile-btn" href="#home" title="Hide navigation">Hide navigation</a>

         <ul id="nav" className="nav">
            <li className="current"><a className="smoothscroll" href="#home">Home</a></li>
            {/* <li><a className="smoothscroll" href="#about">About</a></li> */}
            {/* <li><a className="smoothscroll" href="#resume">Resume</a></li> */}
            <li><a className="smoothscroll" href="#testimonials">Upcomming Events</a></li>
            <li><a className="smoothscroll" href="#contact">Register for Camp</a></li>
         </ul>

      </nav>

      <div className="row banner">
         <div className="banner-text">
            <h1 className="responsive-headline">Welcome to {name}.</h1>
            <h3>{description}.</h3>
            <hr />
            <ul className="social">
               {networks}
            </ul>
         </div>
      </div>

      <p className="scrolldown">
         <a className="smoothscroll" href="#testimonials"><i className="icon-down-circle"></i></a>
      </p>

      </header>
   );
}

export default Header;
