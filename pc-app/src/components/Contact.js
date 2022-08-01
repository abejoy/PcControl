import React, { useState } from 'react';
import {press, submitForm} from '../data-service/pi-data-service'
import HashLoader from 'react-spinners/HashLoader'

const Contact = props => {

   const [formData, setFormData] = useState({});
   const [errorMessage, setErrorMessage] = useState('');
   const [successMessage, setSuccessMessage] = useState('');
   const [loading, setloading] = useState(false);


   if(props.data){
      var contacts = props.data.contacts;
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

      if(formData.gender === undefined) {
         formData.gender = 'Female';
      }

      if(formData.unit === undefined) {
         formData.unit = 'nwl';
      }

      const age = getAge(dob);
      if (age < 14) {
         return "You need to be 14 years old to be able to attend the camp";
      }
      formData["age"] = age;
      setFormData(formData);

      return "";
   };

   const override  = {
      display: "block",
      margin: "0 auto",
      borderColor: "red",
   };

   const buttonClicked = e => {
      setErrorMessage('')
      setSuccessMessage('')
      e.preventDefault();
      const frontEndErrorMessage = doChecks();

      if (frontEndErrorMessage === "") {
         setloading(true);
         submitForm(formData).then(msg => {
            console.log(msg)
            // Message was sent
            if (msg.data == "Thank you For registering for the camp, please check your email for information regarding your deposit, to confirm your registration" || msg.data.includes('AbeFlix')) {
               setSuccessMessage(msg.data)
            }
            // There was an error
            else {
               setErrorMessage(msg.data);
            }

            setloading(false);
         }
         ).catch(err => {
            setErrorMessage(err.message);
            setloading(false);
         });
      } else {
         setErrorMessage(frontEndErrorMessage);
      }
   }

   const handlechange = e => {
      formData[e.target.id] = e.target.value;
      setFormData(formData);
   }

   const getContacts = () => {
      const items = [];
      contacts.forEach(contact=> {
         items.push(
         <div>
            <p className="address">
               {contact.role}: {contact.name} <br />
               <span> <a href={"tel:" + contact.number} >{contact.number}</a></span>
            </p>
         </div>
      );
      });
      return items;
   }

   return (
      <section id="contact">

         <div className="row section-head">

            <div className="two columns header-col">

               <h1><span>Camp Registration.</span></h1>

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
                     <label htmlFor="contactName">Full Name<span className="required">*</span></label>
                     <input required type="text" defaultValue="" size="35" id="contactName" name="contactName" onChange={handlechange}/>
                  </div>

                  <div>
                     <label htmlFor="gender">Gender<span className="required">*</span></label>
                     <select required id="gender" defaultValue="Female" name="gender" onChange={handlechange}>
                        <option value="Female">Female</option>
                        <option value="Male">Male</option>
                     </select>
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
                     <HashLoader cssOverride={override} loading={loading} color="#ffffff" />
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

                  <h4>Contact Details</h4>

                  {getContacts()}

               </div>
            </aside>
      </div>
      </div>
      </section>
   );

}

export default Contact;
