import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function Register() {
  const navigate = useNavigate();

  const [data, setData] = useState({
    username: "",
    email: "",
    password: ""
  });

  const register = async () => {
    try {
      await axios.post("http://localhost:8080/auth/register", data);

      alert("✅ Registered successfully!");
      navigate("/"); // go to login
    } catch (err) {
      alert("❌ Registration failed");
      console.error(err);
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-card">
        <h2>📝 Register</h2>
        <p style={{ opacity: 0.7 }}>Create your account 🚀</p>

        <input
          placeholder="Username"
          onChange={(e) =>
            setData({ ...data, username: e.target.value })
          }
        />

        <input
          placeholder="Email"
          onChange={(e) =>
            setData({ ...data, email: e.target.value })
          }
        />

        <input
          type="password"
          placeholder="Password"
          onChange={(e) =>
            setData({ ...data, password: e.target.value })
          }
        />

        <button onClick={register}>Register</button>

        {/* 🔥 Back to login */}
        <p style={{ marginTop: "15px", fontSize: "14px" }}>
          Already have an account?{" "}
          <span
            style={{ color: "#4cc9f0", cursor: "pointer" }}
            onClick={() => navigate("/")}
          >
            Login
          </span>
        </p>
      </div>
    </div>
  );
}