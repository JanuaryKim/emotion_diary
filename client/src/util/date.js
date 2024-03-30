export const getStrDate = (Date) => {
  return Date.toISOString().slice(0, 10);
};
