import { useEffect, useState } from "react";
import axios from "axios";
import Navbar from "../components/Navbar";

export default function Dashboard() {
  const [url, setUrl] = useState("");
  const [urls, setUrls] = useState([]);

  const token = localStorage.getItem("token");

  // Load URLs
  useEffect(() => {
    const fetchUrls = async () => {
      const res = await axios.get("http://localhost:8080/my-urls", {
        headers: { Authorization: `Bearer ${token}` }
      });
      setUrls(res.data);
    };
    fetchUrls();
  }, [token]);

  // Shorten
  const shorten = async () => {
    await axios.post("http://localhost:8080/shorten",
      { url },
      { headers: { Authorization: `Bearer ${token}` } }
    );

    reload();
  };

  const reload = async () => {
    const res = await axios.get("http://localhost:8080/my-urls", {
      headers: { Authorization: `Bearer ${token}` }
    });
    setUrls(res.data);
  };

  const deleteUrl = async (shortUrl) => {
    const code = shortUrl.split("/").pop();

    await axios.delete(`http://localhost:8080/${code}`, {
      headers: { Authorization: `Bearer ${token}` }
    });

    reload();
  };

  return (
    <>
      <Navbar />

      <div className="container">
        <h2>🚀 Your URLs</h2>

        {/* Input Card */}
        <div className="card">
          <input
            placeholder="Paste your long URL here..."
            onChange={(e) => setUrl(e.target.value)}
          />
          <button onClick={shorten}>Shorten</button>
        </div>

        {/* Grid Layout */}
        <div className="grid">
          {urls.map((u, i) => (
            <div className="card" key={i}>
              <p>{u.originalUrl}</p>

              <a href={u.shortUrl} target="_blank" rel="noreferrer">
                {u.shortUrl}
              </a>

              <p>📊 Clicks: {u.clickCount}</p>

              <button onClick={() => {
                navigator.clipboard.writeText(u.shortUrl);
                alert("Copied!");
              }}>
                📋 Copy
              </button>

              <button onClick={() => deleteUrl(u.shortUrl)}>
                🗑 Delete
              </button>

              <img
                className="qr"
                src={`http://localhost:8080/qr/${u.shortUrl.split("/").pop()}`}
                alt="QR"
                width="100"
              />
            </div>
          ))}
        </div>
      </div>
    </>
  );
}