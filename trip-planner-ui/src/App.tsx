import "./../app/globals.css";
import { ThemeProvider } from "@/components/theme-provider";
import Places from "./place/places";
import Header from "./header/header";
import { useState } from "react";




function App() {
  const [location, setLocation] = useState("London");
  const [startDate, setStartDate] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date(new Date().setDate(new Date().getDate() + 6)));

  const onSearch = (location: string, startDate: Date, endDate: Date) => {
    setLocation(location);
    setStartDate(startDate);
    setEndDate(endDate);
  };

  return (
    <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
      <Header onSearch={onSearch} />
      <Places location={location} startDate={startDate} endDate={ endDate} />
    </ThemeProvider>
  );
}

export default App;
