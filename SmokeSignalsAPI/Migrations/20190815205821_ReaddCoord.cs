using Microsoft.EntityFrameworkCore.Migrations;

namespace SmokeSignalsAPI.Migrations
{
    public partial class ReaddCoord : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<double>(
                name: "LC_Latitude",
                table: "Users",
                nullable: false,
                defaultValue: 0.0);

            migrationBuilder.AddColumn<double>(
                name: "LC_Longitude",
                table: "Users",
                nullable: false,
                defaultValue: 0.0);
        }

    }
}
