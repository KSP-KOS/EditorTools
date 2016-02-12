using System;
using System.Text.RegularExpressions;

namespace kOSMinimiser
{
    class Program
    {
        static string filename;

        static void Main(string[] args)
        {
            try
            {
                filename = args[0];
            }
            catch
            {
                Console.WriteLine("Sorry, There seems to be a problem with your syntax. You need to run kOSminimiser.exe filename.ks");
                Console.WriteLine("Press enter to close...");
                Console.ReadLine();
                Environment.Exit(0);
            }

            Minimiser.Minimise(filename);
        }
    }

    class Minimiser
    {
        static public void Minimise(string filename)
        {
            //Open file into string s
            string s = Open(filename);
            int initial = s.Length * sizeof(Char);

            //Remove comments
            s = Regex.Replace(s, @"//(.*?)\r?\n", me => {
                if (me.Value.StartsWith("//"))
                    return me.Value.StartsWith("//") ? Environment.NewLine : "";
                return me.Value;
            }, RegexOptions.Singleline);

            //Remove double spaces
            s = s.Replace("  ", " ");

            //Remove lines containing only whitespace
            s = Regex.Replace(s, @"^\s+$[\r\n]*", "", RegexOptions.Multiline);

            int final = s.Length * sizeof(Char);

            Save(filename, s);
            Console.WriteLine("Initial size: " + initial.ToString() + " bytes\r\nFinal size: " + final.ToString() + " bytes\r\nSaved: " + (initial - final).ToString() +" bytes");
            Console.WriteLine("Press enter to close...");
            Console.ReadLine();
        }

        static string Open(string filename)
        {
            try {
                string s;
                System.IO.StreamReader objReader;
                objReader = new System.IO.StreamReader(filename);
                s = objReader.ReadToEnd();
                objReader.Close();
                return s;
            }catch
            {
                Console.WriteLine("There was a problem opening the script. Press enter to continue");
                Console.ReadLine();
                Environment.Exit(0);
                return null;
            }
        }

        static void Save(string filename, string buffer)
        {
            filename = filename.Remove(filename.Length - 3);
            filename = filename + "-minimised.ks";
            System.IO.File.WriteAllText(filename, buffer);
        }
    }
}
