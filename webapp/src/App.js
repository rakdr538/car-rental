import logo from './logo.svg';
import './App.css';
import {Route, Routes} from 'react-router-dom';
import Navbar from './components/Navbar';
import GetAvailableCars from './components/GetAvailableCars';
import Home from './pages/Home';
import Admin from './pages/Admin';

function App() {

  return (
    <div className="App">
      <Navbar/>
      <div className="content">
        <Routes>
            <Route path="/" element={< Home />} />
            <Route path="/rent" element={< GetAvailableCars />} />
            <Route path="/admin" element={< Admin />} />
        </Routes>
      </div>
    </div>
  );
}

export default App;
