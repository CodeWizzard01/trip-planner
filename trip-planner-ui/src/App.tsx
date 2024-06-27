import "./../app/globals.css";
import { ThemeProvider } from "@/components/theme-provider";
import Places from "./place/places";
import Header from "./header/header";
import { useState } from "react";



function App() {
  const [location, setLocation] = useState("London");
  const [travelDate, setTravelDate] = useState(new Date());

  const onSearch = (location: string, travelDate: Date) => {
    setLocation(location);
    setTravelDate(travelDate);
  }

  return (
    <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
      <Header onSearch={onSearch} />
      <Places location={location}  travelDate={travelDate} />
    </ThemeProvider>
  );
}

export default App;
