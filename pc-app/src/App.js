import React, { useEffect } from 'react';
import ReactGA from 'react-ga';
import './App.css';
import Header from './components/Header';
import Footer from './components/Footer';
import About from './components/About';
import Resume from './components/Resume';
import Contact from './components/Contact';
import Testimonials from './components/Testimonials';
import resumeData from './assets/resumeData.json'

function App() {


  useEffect(() => {
    ReactGA.initialize('UA-110570651-1');
    ReactGA.pageview(window.location.pathname);
  }, []);


  return (
    
    <div className="App">
      <Header data={resumeData.main}/>
      {/* <About data={resumeData.main}/> */}
      {/* <Resume data={resumeData.resume}/> */}
      <Testimonials data={resumeData.testimonials}/>
      <Contact data={resumeData.main}/>
      <Footer data={resumeData.main}/>
    </div>
  );

}

export default App;
