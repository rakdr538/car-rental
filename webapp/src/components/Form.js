import React, { useState } from "react";
import axios from 'axios';

export default function Form(props) {
const plateNo = props.plateNo;
const fromDate = props.fromDate;
const toDate = props.toDate;

const [nameError, setNameError] = useState();
const [emailError, setEmailError] = useState();
const [ageError, setAgeError] = useState();
const [formData, setFormData] = useState({name: "",email: "",age: 0});
const [rental, setRental] = useState();
const [error, setError] = React.useState(null);

const handleChange = (event) => {
    const { name, value } = event.target;
    // do proper validation here
    setFormData((prevFormData) => ({ ...prevFormData, [name]: value }));
};

const handleSubmit = (event) => {
        event.preventDefault();
        if (formData.name && formData.email && formData.age && plateNo && fromDate && toDate) {
            var request = {
                        vehiclePlateNo: plateNo,
                        age: formData.age,
                        name: formData.name,
                        email: formData.email,
                        fromDate: fromDate,
                        toDate: toDate
                    }
            console.log(request)

            axios({
                        method: "post",
                        url: "http://localhost:8080/api/v1/rent",
                        data: JSON.stringify(request),
                        headers: { "Content-Type": "application/json" },
                      })
                        .then(response => {
                          setRental(response.data);
                          console.log(response.data);
                        })
                        .catch(response => {
                          setError(response);
                          console.log(response);
                        });
        } else {
             setError("Could not submit form with out mandatory fields!");
        }
    console.log(rental);
};

return (
    <div>
        {rental ? (
            <div>
            <h1>Thank you for booking {rental.manufacturedBy} {rental.vehicleModel} from {rental.fromDate} to {rental.toDate}!</h1>
            <h2>You have booked for {rental.totalNoOfDays} days and expected cost is {rental.expectedTotalPrice}SEK</h2>
            <br/>
            <h3>Feel free to book more!</h3>
            </div>
          ) : (
          <div>
                <form onSubmit={handleSubmit}>
                  <label htmlFor="name">Name:</label>
                  <input type="text" id="name" name="name" value={formData.name} onChange={handleChange} required={true} minLength={3} maxLength={100} />
                  <span style={{ color: "red" }}>{nameError}</span>
                  <br />

                  <label htmlFor="email">Email:</label>
                  <input type="email" id="email" name="email" value={formData.email} onChange={handleChange} required={true}/>
                  <span style={{ color: "red" }}>{emailError}</span>
                  <br />

                  <label htmlFor="age">Age:</label>
                  <input id="age" name="age" type="number" value={formData.age} onChange={handleChange} min="18" max="80" step="1" required={true}/>
                  <span style={{ color: "red" }}>{ageError}</span>
                  <br />

                  <button type="submit">Book Now!</button>
                </form>
           </div>
          )}
    </div>
  );
}