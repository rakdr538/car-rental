import axios from "axios";
import React from "react";

export default function Admin() {
const [rentals, setRentals] = React.useState(null);
const [error, setError] = React.useState(null);
const [revenue, setRevenue] = React.useState(0);

  React.useEffect(() => {
    // invalid url will trigger an 404 error
    axios.get('http://localhost:8080/api/v1/admin').then((response) => {
      setRentals(response.data);
      if (response.data) {
          let total = 0;
          response.data.map((rental) => total = total + rental.expectedTotalPrice);
          setRevenue(total);
        }
    }).catch(error => {
      setError(error);
      console.log(error);
    });
  }, []);

  if (error) return `Error: ${error.message}`
  if (!rentals) return "No Rentals!"

    return (
      <div>
        <div>

        <table>
          <thead>
            <tr>
              <th scope="col">Name of Driver</th>
              <th scope="col">Car</th>
              <th scope="col">Booked From</th>
              <th scope="col">Booked To</th>
              <th scope="col">Booked for in days</th>
              <th scope="col">Revenue</th>
            </tr>
          </thead>
          {   rentals.map((rental) => (
                          <tbody>
                            <tr>
                              <th scope="row">{rental.name}</th>
                              <td>{rental.manufacturedBy} {rental.vehicleModel}</td>
                              <td><time datetime={rental.fromDate}>{rental.fromDate}</time></td>
                              <td><time datetime={rental.toDate}>{rental.toDate}</time></td>
                              <td>{rental.totalNoOfDays}</td>
                              <td>{rental.expectedTotalPrice}</td>
                            </tr>
                          </tbody>
          ))}
        </table>
        </div>
        <div>
            Total Revenue: {revenue}
        </div>
      </div>
    );
}