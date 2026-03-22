import { useNavigate } from "react-router-dom";

export default function Navbar() {
  const navigate = useNavigate();

  const logout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  return (
    <div className="navbar">
      <div className="logo">🔗 Shortify</div>
      <button className="logout-btn" onClick={logout}>
        Logout
      </button>
    </div>
  );
}