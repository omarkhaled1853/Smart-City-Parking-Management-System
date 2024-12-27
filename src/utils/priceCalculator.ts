export const calculatePrice = (basePrice: number, startDate: Date, duration: number): number => {
  const endDate = new Date(startDate.getTime() + duration * 60 * 60 * 1000);
  let totalPrice = 0;
  let currentDate = new Date(startDate);

  while (currentDate < endDate) {
    const hour = currentDate.getHours();
    const day = currentDate.getDay();
    let hourlyRate = basePrice;

    // Weekend pricing (Friday = 5, Saturday = 6)
    if (day === 5 || day === 6) {
      hourlyRate *= 1.5;
    }

    // Peak hours pricing (8 PM - 12 AM)
    if (hour >= 20 && hour <= 23) {
      hourlyRate *= 1.25;
    }

    totalPrice += hourlyRate;
    currentDate.setHours(currentDate.getHours() + 1);
  }

  return totalPrice;
};