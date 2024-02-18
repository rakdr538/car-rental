import './Navbar.css';
import { Link, useMatch, useResolvedPath } from 'react-router-dom';

export default function Navbar() {
    return (
        <div className="navbar">
            <Link to="/" className="site-title"><h1>Car Rentals</h1></Link>
            <ul>
                <CustomLink to="/rent">Rent a Car</CustomLink>
                <CustomLink to="/admin">Owner</CustomLink>
            </ul>
        </div>
    );
}

function CustomLink({to, children, ...props}){
    const resolvedPath = useResolvedPath(to);
    const isActive = useMatch({ path: resolvedPath.pathname, end: true });

    return (
        <li className = {isActive ? "active" : ""}>
            <Link to={to} className="navbar-item" {...props} >{children}</Link>
        </li>
    );
}