import React from "react";
import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormMessage,
} from "@/components/ui/form";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { Input } from "@/components/ui/input";
import { Calendar } from "@/components/ui/calendar";
import { CalendarIcon } from "lucide-react";
import { format } from "date-fns";
import { cn } from "../lib/utils";
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { ModeToggle } from "../components/mode-toggle";

type HeaderProps = {
  onSearch: (location: string, travelDate: Date) => void;
};

function Header({ onSearch }: HeaderProps) {
  const formSchema = z.object({
    location: z.string().min(3, {
      message: "Location must be at least 3 characters.",
    }),
    travelDate: z.date({
      required_error: "Travel date is required.",
    }),
  });

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      location: "London",
      travelDate: new Date(),
    },
  });

  function onSubmit(values: z.infer<typeof formSchema>) {
    onSearch(values.location, values.travelDate);
  }

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)}>
        <div className="flex mb-4 space-y-5 items-end">
          <div className="w-1/4 h-12">
            <ModeToggle />
          </div>
          <div className="w-1/4 h-12">
            <FormField
              control={form.control}
              name="location"
              render={({ field }) => (
                <FormItem>
                  <FormControl>
                    <Input
                      placeholder="Location"
                      {...field}
                      className="w-1/2"
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
          </div>
          <div className="w-1/4 h-12">
            <FormField
              control={form.control}
              name="travelDate"
              render={({ field }) => (
                <FormItem>
                  <Popover>
                    <PopoverTrigger asChild>
                      <FormControl>
                        <Button
                          variant={"outline"}
                          className={cn(
                            "w-[240px] pl-3 text-left font-normal",
                            !field.value && "text-muted-foreground"
                          )}
                        >
                          {field.value ? (
                            format(field.value, "PPP")
                          ) : (
                            <span>Travel Date</span>
                          )}
                          <CalendarIcon className="ml-auto h-4 w-4 opacity-50" />
                        </Button>
                      </FormControl>
                    </PopoverTrigger>
                    <PopoverContent className="w-auto p-0" align="start">
                      <Calendar
                        mode="single"
                        selected={field.value}
                        onSelect={field.onChange}
                        disabled={(date) => {
                          const minDate = new Date();
                          minDate.setDate(minDate.getDate() + 1); // Set minDate to T+1
                          minDate.setHours(0, 0, 0, 0); // Normalize minDate to the start of T+1 day

                          const maxDate = new Date();
                          maxDate.setDate(maxDate.getDate() + 6); // Set maxDate to T+6
                          maxDate.setHours(23, 59, 59, 999); // Set maxDate to the end of T+6 day

                          return date < minDate || date > maxDate;
                        }}
                        initialFocus
                      />
                    </PopoverContent>
                  </Popover>
                  <FormMessage />
                </FormItem>
              )}
            />
          </div>
          <div className="w-1/4 h-12">
            <Button type="submit">Search</Button>
          </div>
        </div>
      </form>
    </Form>
  );
}

export default Header;
