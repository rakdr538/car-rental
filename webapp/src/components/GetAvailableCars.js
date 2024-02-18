import React, { useState } from "react";
import DatePicker from "react-datepicker";
import { addDays, addMonths, format } from 'date-fns';
import "react-datepicker/dist/react-datepicker.css";
import axios from 'axios';
import Accordion from './Accordion';
import "./GetAvailableCars.css";

export default function GetAvailableCars() {
    const [startDate, setStartDate] = useState(addDays(new Date(), 1));
    const [endDate, setEndDate] = useState(addDays(startDate, 1));
    const [vehicles, setVehicles] = useState();
    const [error, setError] = React.useState(null);

    let vehicleAccordion;

    const getAllAvailableVehicles = () => {
        axios.get('http://localhost:8080/api/v1/rent', {
                                                         params: {
                                                           from: format(startDate, "yyyy-MM-dd"),
                                                           to: format(endDate, "yyyy-MM-dd")
                                                         }
                                                       })
              .then(response => {
                setVehicles([response.data]);
              })
              .catch(error => {
              setError(error);
                console.error(error);
              });
    };

    const onChangeStartDate = (date) => {
        setStartDate(date);
        if (date.getTime() > endDate.getTime()) {
            setEndDate(addDays(date, 1));
        }
      };

      if (vehicles && vehicles.length > 0) {
        vehicleAccordion = <div> <Accordion vehicles={vehicles} startDate={format(startDate, "yyyy-MM-dd")} endDate={format(endDate, "yyyy-MM-dd")}/> </div>
      } else {
        vehicleAccordion = null
      }

      if (error) return `Error: ${error.message}`;

      return (
      <div>
        <p>From</p>
        <DatePicker
          showIcon
          selected={startDate}
          minDate={addDays(new Date(), 1)}
          maxDate={addMonths(new Date(), 12)}
          onChange={onChangeStartDate}
          dateFormat='yyyy-MM-dd'
        />
        <p>To</p>
                <DatePicker
                  showIcon
                  selected={endDate}
                  minDate={endDate}
                  maxDate={addMonths(new Date(), 12)}
                  onChange={(date) => setEndDate(date)}
                  dateFormat='yyyy-MM-dd'
                />
        <div style={{
                 paddingTop: '50px',
                 boxSizing: 'content-box',
               }}>
            <p>you wish to reserve vehicle between: {format(startDate, "yyyy-MM-dd")} and {format(endDate, "yyyy-MM-dd")}</p>
            <button onClick={getAllAvailableVehicles}>OK</button>
        </div>
            {vehicleAccordion}
        </div>
      );
}
