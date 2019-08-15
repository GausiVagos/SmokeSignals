using Microsoft.EntityFrameworkCore.Migrations;

namespace SmokeSignalsAPI.Migrations
{
    public partial class RemovedCoord : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "LC_Latitude",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "LC_Longitude",
                table: "Users");
        }

    }
}
