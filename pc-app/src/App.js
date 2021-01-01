import React, { useEffect } from 'react';
import ReactGA from 'react-ga';
import './App.css';
import Header from './Components/Header';
import Footer from './Components/Footer';
import About from './Components/About';
import Resume from './Components/Resume';
import Contact from './Components/Contact';
import Testimonials from './Components/Testimonials';
import resumeData from './assets/resumeData.json'

function App() {


  useEffect(() => {
    ReactGA.initialize('UA-110570651-1');
    ReactGA.pageview(window.location.pathname);
  }, []);


  return (
    
    <div className="App">
      <Header data={resumeData.main}/>
      <About data={resumeData.main}/>
      <Resume data={resumeData.resume}/>
      <Testimonials data={resumeData.testimonials}/>
      <Contact data={resumeData.main}/>
      <Footer data={resumeData.main}/>
    </div>
  );

}

export default App;
