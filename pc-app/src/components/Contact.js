import React, { useState } from 'react';
import {press, submitForm} from '../data-service/pi-data-service'

const Contact = props => {

   const [formData, setFormData] = useState({});
   const [errorMessage, setErrorMessage] = useState('');
   const [successMessage, setSuccessMessage] = useState('');


   if(props.data){
      var name = props.data.name;
      var street = props.data.address.street;
      var city = props.data.address.city;
      var state = props.data.address.state;
      var zip = props.data.address.zip;
      var phone= props.data.phone;
      var email = props.data.email;
      var message = props.data.contactmessage;
   }

   const buttonClicked = e => {
      setErrorMessage('')
      setSuccessMessage('')
      e.preventDefault();
      console.log(document.getElementById('image-loader'));
      submitForm(formData).then(msg => {
         // Message was sent
         if (msg.data == "OK") {
            setSuccessMessage(msg.data)
         }
         // There was an error
         else {
            setErrorMessage('there was an error')
         }
      }).catch(err => {
         setErrorMessage(err.message);
      });
   }

   const handlechange = e => {
      formData[e.target.id] = e.target.value;
      setFormData(formData);
   }

   return (
      <section id="contact">

         <div className="row section-head">

            <div className="two columns header-col">

               <h1><span>Get In Touch.</span></h1>

            </div>

            <div className="ten columns">

                  <p className="lead">{message}</p>

            </div>

         </div>

         <div className="row">
            <div className="eight columns">

               <form onSubmit={buttonClicked} id="contactForm" name="contactForm">
               <fieldset>

                  <div>
                     <label htmlFor="contactName">Name <span className="required">*</span></label>
                     <input type="text" defaultValue="" size="35" id="contactName" name="contactName" onChange={handlechange}/>
                  </div>

                  <div>
                     <label htmlFor="contactEmail">Email <span className="required">*</span></label>
                     <input type="text" defaultValue="" size="35" id="contactEmail" name="contactEmail" onChange={handlechange} />
                  </div>

                  <div>
                     <label htmlFor="contactSubject">Subject</label>
                     <input type="text" defaultValue="" size="35" id="contactSubject" name="contactSubject" onChange={handlechange} />
                  </div>

                  <div>
                     <label htmlFor="contactMessage">Message <span className="required">*</span></label>
                     <textarea cols="50" rows="15" id="contactMessage" name="contactMessage" onChange={handlechange}></textarea>
                  </div>

                  <div>
                     <button className="submit">Submit</button>
                     <span id="image-loader">
                        <img alt="" src="images/loader.gif" />
                     </span>
                  </div>
               </fieldset>
               </form>

               {
            errorMessage !== '' &&
            (<div id="message-warning"> {errorMessage}</div>)
            }

            {
            successMessage !== '' &&
            (               
            <div id="message-success">
               <i className="fa fa-check"></i>Your message was sent, thank you!<br />
            </div>
            )
            }


            <aside className="four columns footer-widgets">
               <div className="widget widget_contact">

                  <h4>Address and Phone</h4>
                  <p className="address">
                     {name}<br />
                     {street} <br />
                     {city}, {state} {zip}<br />
                     <span>{phone}</span>
                  </p>
               </div>
            </aside>
      </div>
      </div>
      </section>
   );

}

export default Contact;
