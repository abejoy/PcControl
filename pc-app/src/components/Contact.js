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

   const getAge = (dateString) => {
      var today = new Date();
      var birthDate = new Date(dateString);
      var age = today.getFullYear() - birthDate.getFullYear();
      var m = today.getMonth() - birthDate.getMonth();
      if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
          age--;
      }
      return age;
  };

   const doChecks = () => {
      const dob = formData.dob;
      const age = getAge(dob);
      if (age < 14) {
         return "You need to be 14 years old to be able to attend the camp";
      }
      formData["age"] = age;
      setFormData(formData);

      return "";
   };

   const buttonClicked = e => {
      setErrorMessage('')
      setSuccessMessage('')
      e.preventDefault();

      

      console.log(formData);
      const frontEndErrorMessage = doChecks();

      if (frontEndErrorMessage === "") {
         submitForm(formData).then(msg => {
            // Message was sent
            if (msg.data == "Your message was sent, thank you!" || msg.data.includes('AbeFlix')) {
               setSuccessMessage(msg.data)
            }
            // There was an error
            else {
               setErrorMessage('there was an error')
            }
         }
         ).catch(err => {
            setErrorMessage(err.message);
         });
      } else {
         setErrorMessage(frontEndErrorMessage);
      }

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
                     <input required type="text" defaultValue="" size="35" id="contactName" name="contactName" onChange={handlechange}/>
                  </div>

                  <div>
                     <label htmlFor="contactEmail">Email <span className="required">*</span></label>
                     <input required type="text" defaultValue="" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" size="35" id="contactEmail" name="contactEmail" onChange={handlechange} />
                  </div>

                  <div>
                     <label htmlFor="contactPhone">Phone number <span className="required">*</span></label>
                     <input required type="tel" placeholder="07638556432" pattern="[0-9]{11}" size="35" id="contactPhone" name="contactPhone" onChange={handlechange} />
                  </div>

                  <div>
                     <label htmlFor="parentPhone">Parent/Guardian contact number <span className="required">*</span></label>
                     <input required type="tel" placeholder="07638556432" pattern="[0-9]{11}" size="35" id="parentPhone" name="parentPhone" onChange={handlechange} />
                  </div>

                  <div>
                     <label htmlFor="dob">Date of Birth <span className="required">*</span></label>
                     <input required type="date" defaultValue="" size="35" id="dob" name="dob" onChange={handlechange} />
                  </div>

                  <div>
                     <label htmlFor="unit">Koodarayogyam Unit<span className="required">*</span></label>
                     <select required id="unit" defaultValue="nwl" name="unit" onChange={handlechange}>
                        <option value="nwl">North West London</option>
                        <option value="el">East London</option>
                        <option value="harlow">Harlow</option>
                        <option value="stevenage">Stevenage</option>
                        <option value="basildon">Basildon</option>
                     </select>
                  </div>

                  <div>
                     <label htmlFor="contactMessage">Additional Message or Comments </label>
                     <textarea cols="50" rows="15" id="contactMessage" name="contactMessage" onChange={handlechange}></textarea>
                  </div>

                  <div>
                     <button className="submit" >Submit</button>
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
               <i className="fa fa-check"></i>{successMessage}<br />
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
