import { useState } from 'react';
import Form from './Form';

export default function Accordion(props) {
  const vehicles = props.vehicles[0];
  const fromDate = props.startDate;
  const toDate = props.endDate;
  const [activeIndex, setActiveIndex] = useState();

  const arrayDataItems = [];
  for (let i=0; i < vehicles.length; i++) {
    arrayDataItems.push(
        <Panel
        title= {`${vehicles[i].manufacturedBy} ${vehicles[i].vehicleModel} has 5 seats and will cost you ${vehicles[i].pricePerDay}SEK per day`}
        isActive={activeIndex === i}
        plateNo={vehicles[i].vehiclePlateNo}
        fromDate={fromDate}
        toDate={toDate}
        onShow={() => setActiveIndex(i)} />
    )}

  return (
    <div>
        <div>
            {arrayDataItems}
        </div>
    </div>
  );
}

function Panel({
  title,
  isActive,
  plateNo,
  fromDate,
  toDate,
  onShow
}) {
  return (
    <section className="panel">
      <h4>{title}</h4>
      {isActive ? (
        <Form plateNo={plateNo} fromDate={fromDate} toDate={toDate}/>
      ) : (
        <button onClick={onShow}>
          Select
        </button>
      )}
    </section>
  );
}
